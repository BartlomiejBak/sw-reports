package pl.bartekbak.swreports.service.consumer;

import org.springframework.stereotype.Service;
import pl.bartekbak.swreports.consumer.SWApiConsumer;
import pl.bartekbak.swreports.service.converter.PersonToResultConverter;
import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Planet;
import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;
import pl.bartekbak.swreports.dto.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryConsumerImpl implements QueryConsumer {

    private static final String GET_PERSON_URI = "http://localhost:8080/api/people/?search=";
    private static final String GET_PLANET_URI = "http://localhost:8080/api/planets/?search=";

    PersonToResultConverter converter;
    SWApiConsumer consumer;

    public QueryConsumerImpl(PersonToResultConverter converter, SWApiConsumer consumer) {
        this.converter = converter;
        this.consumer = consumer;
    }

    @Override
    public Report createReport(Query query) {
        String characterQuery = query.getCharacterQueryCriteria();
        String planetQuery = query.getPlanetQueryCriteria();

        Report report = Report.builder()
                .characterQueryCriteria(characterQuery)
                .planetQueryCriteria(planetQuery)
                .build();

        List<Planet> planets = consumer.getPlanets(GET_PLANET_URI + planetQuery);

        if (planets.isEmpty()) return report;
        List<Person> personList = consumer.getPersons(GET_PERSON_URI + characterQuery);
        List<Person> filteredPersonList = filterPlanet(personList, planets);

        report.setResultList(getResults(filteredPersonList));

        return report;
    }

    private List<Result> getResults(List<Person> personList) {
        List<Result> resultList = new ArrayList<>();
        for (Person person : personList) {
            resultList.addAll(converter.convertToResultList(person));
        }
        return resultList;
    }

    private List<Person> filterPlanet(List<Person> personList, List<Planet> planets) {
        List<Person> filteredPersons = new ArrayList<>();
        for (Planet planet : planets) {
            filteredPersons.addAll(personList.stream()
                    .filter(person -> person.getHomeworld().equals(planet.getUrl()))
                    .collect(Collectors.toList()));
        }
        return filteredPersons;
    }

}
