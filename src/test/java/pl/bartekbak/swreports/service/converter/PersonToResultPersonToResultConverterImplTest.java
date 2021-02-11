package pl.bartekbak.swreports.service.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bartekbak.swreports.consumer.SWApiConsumer;
import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Result;
import pl.bartekbak.swreports.exception.QueryProcessingException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonToResultPersonToResultConverterImplTest {

    @Mock
    private SWApiConsumer consumer;

    @InjectMocks
    private PersonToResultConverterImpl converter;

    private Person anakin;
    private List<Result> results;

    private final String planet = "Tatooine";

    private final String movieILink = "link/4/";
    private final String movieIILink = "link/5/";
    private final String movieIIILink = "link/6/";

    private final String movieI = "The Phantom Menace";
    private final String movieII = "Attack of the Clones";
    private final String movieIII = "Revenge of the Sith";

    @BeforeEach
    void setUp() {

        String name = "Anakin";
        String personLink = "person/11/";
        String planetLink = "planet/1/";
        anakin = Person.builder()
                .name(name)
                .homeworld(planetLink)
                .url(personLink)
                .films(List.of(movieILink, movieIILink, movieIIILink))
                .build();
        Result anakinI = Result.builder()
                .resultId(1100104)
                .characterId(11)
                .characterName(name)
                .planetId(1)
                .planetName(planet)
                .filmId(4)
                .filmName(movieI)
                .build();
        Result anakinII = Result.builder()
                .resultId(1100105)
                .characterId(11)
                .characterName(name)
                .planetId(1)
                .planetName(planet)
                .filmId(5)
                .filmName(movieII)
                .build();
        Result anakinIII = Result.builder()
                .resultId(1100106)
                .characterId(11)
                .characterName(name)
                .planetId(1)
                .planetName(planet)
                .filmId(6)
                .filmName(movieIII)
                .build();
        results = List.of(anakinI, anakinII, anakinIII);
    }

    @Test
    void convertAnakinToResultList_shouldReturnResults() {
        //given
        when(consumer.getPlanetName(anyString())).thenReturn(planet);
        when(consumer.getMovieTitle(movieILink)).thenReturn(movieI);
        when(consumer.getMovieTitle(movieIILink)).thenReturn(movieII);
        when(consumer.getMovieTitle(movieIIILink)).thenReturn(movieIII);
        //when
        List<Result> resultList = converter.convertToResultList(anakin);
        //then
        assertEquals(results, resultList);
    }

    @Test
    void convertAnakinToResultList_getPlanetError_shouldThrowQueryProcessingException() {
        //given
        when(consumer.getPlanetName(anyString())).thenThrow(QueryProcessingException.class);
        //when
        //then
        assertThrows(QueryProcessingException.class, () -> converter.convertToResultList(anakin));
    }
    @Test
    void convertAnakinToResultList_getMovieError_shouldThrowQueryProcessingException() {
        //given
        when(consumer.getMovieTitle(anyString())).thenThrow(QueryProcessingException.class);
        //when
        //then
        assertThrows(QueryProcessingException.class, () -> converter.convertToResultList(anakin));
    }
}
