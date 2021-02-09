package pl.bartekbak.swreports.service.consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bartekbak.swreports.consumer.SWApiConsumer;
import pl.bartekbak.swreports.converter.PersonToResultConverter;
import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Query;
import pl.bartekbak.swreports.dto.Report;
import pl.bartekbak.swreports.dto.Result;
import pl.bartekbak.swreports.exception.QueryProcessingException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryConsumerImplTest {

    @Mock
    private PersonToResultConverter converter;
    @Mock
    private SWApiConsumer apiConsumer;
    @InjectMocks
    private QueryConsumerImpl queryConsumer;

    private Query query;
    private Report report;

    private List<Person> personList = new ArrayList<>();
    private List<Result> resultList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        String character = "Anakin";
        String planet = "Tatooine";

        query = Query.builder()
                .characterQueryCriteria(character)
                .planetQueryCriteria(planet)
                .build();
        Person person = Person.builder()
                .name(character)
                .homeworld(planet)
                .build();
        Result result = Result.builder()
                .characterName(character)
                .planetName(planet)
                .build();
        personList = List.of(person);
        resultList = List.of(result);
        report = Report.builder()
                .characterQueryCriteria(character)
                .planetQueryCriteria(planet)
                .resultList(resultList)
                .build();
    }

    @Test
    void createReport_shouldReturnReport() {
        //given
        when(apiConsumer.getPersonList(anyString())).thenReturn(personList);
        when(converter.convertToResultList(any(Person.class))).thenReturn(resultList);
        //when
        final Report result = queryConsumer.createReport(query);
        //then
        assertEquals(report, result);
    }

    @Test
    void createReport_changedQuery_shouldNotReturnReport() {
        query.setPlanetQueryCriteria("Naboo");
        //given
        when(apiConsumer.getPersonList(anyString())).thenReturn(personList);
        when(converter.convertToResultList(any(Person.class))).thenReturn(resultList);
        //when
        final Report result = queryConsumer.createReport(query);
        //then
        assertNotEquals(report, result);
    }

    @Test
    void createReport_shouldThrowQueryProcessingException() {
        //given
        when(apiConsumer.getPersonList(anyString())).thenThrow(QueryProcessingException.class);
        //when
        //then
        assertThrows(QueryProcessingException.class, () -> queryConsumer.createReport(query));
    }


}
