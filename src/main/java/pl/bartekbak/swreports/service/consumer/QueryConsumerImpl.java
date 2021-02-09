package pl.bartekbak.swreports.service.consumer;

import org.springframework.stereotype.Service;
import pl.bartekbak.swreports.consumer.SWApiConsumer;
import pl.bartekbak.swreports.converter.PersonToResultConverter;
import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;
import pl.bartekbak.swreports.dto.Result;
import pl.bartekbak.swreports.exception.QueryProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryConsumerImpl implements QueryConsumer {

    PersonToResultConverter converter;
    SWApiConsumer client;

    public QueryConsumerImpl(PersonToResultConverter converter, SWApiConsumer client) {
        this.converter = converter;
        this.client = client;
    }

    @Override
    public Report createReport(Query query) {
        String characterQuery = query.getCharacterQueryCriteria();

        Report report = Report.builder()
                .characterQueryCriteria(characterQuery)
                .planetQueryCriteria(query.getPlanetQueryCriteria())
                .build();
        try {
            String address = "http://localhost:8080/api/people/?search=";
            List<Person> personList = client.getPersonList(address + characterQuery);
            report.setResultList(getResults(query, personList));
        } catch (Exception e) {
            throw new QueryProcessingException(e.getMessage());
        }
        return report;
    }

    private List<Result> getResults(Query query, List<Person> personList) throws Exception {
        List<Result> resultList = new ArrayList<>();
        for (Person person : personList) {
            resultList.addAll(converter.convertToResultList(person).stream()
                    .filter(result -> result.getPlanetName().equals(query.getPlanetQueryCriteria()))
                    .collect(Collectors.toList()));
        }
        return resultList;
    }

}
