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
    private SWApiConsumer apiClient;
    @InjectMocks
    private QueryConsumerImpl queryClient;

    private Query query;
    private Person person;
    private Result result;
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
        person = Person.builder()
                .name(character)
                .homeworld(planet)
                .build();
        result = Result.builder()
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
    void createReport_shouldReturnReport() throws Exception {
        //given
        when(apiClient.getPersonList(anyString())).thenReturn(personList);
        when(converter.convertToResultList(any(Person.class))).thenReturn(resultList);
        //when
        final Report result = queryClient.createReport(query);
        //then
        assertEquals(report, result);
    }

    @Test
    void createReport_changedQuery_shouldNotReturnReport() throws Exception {
        query.setPlanetQueryCriteria("Naboo");
        //given
        when(apiClient.getPersonList(anyString())).thenReturn(personList);
        when(converter.convertToResultList(any(Person.class))).thenReturn(resultList);
        //when
        final Report result = queryClient.createReport(query);
        //then
        assertNotEquals(report, result);
    }

    @Test
    void createReport_shouldThrowRuntimeException() throws Exception {
        //given
        when(apiClient.getPersonList(anyString())).thenThrow(RuntimeException.class);
        //when
        //then
        assertThrows(RuntimeException.class, () -> queryClient.createReport(query));
    }


}
