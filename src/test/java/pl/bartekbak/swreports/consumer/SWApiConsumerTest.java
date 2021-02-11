package pl.bartekbak.swreports.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Planet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@RestClientTest
class SWApiConsumerTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    SWApiConsumer consumer;

    private MockRestServiceServer mockServer;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getPlanets_shouldReturnPlanets() throws Exception {
        String query = "http://localhost:8080/api/planets/?search=Tatooine";

        List<Planet> planets = List.of(Planet.builder()
                .name("Tatooine")
                .url("http://localhost:8080/api/planets/1/")
                .build());
        String json = mapper.writeValueAsString(planets);
        mockServer
                .expect(requestTo(query))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        List<Planet> result = consumer.getPlanets(query);
        assertEquals(planets, result);
    }

    @Test
    void getPersons_shouldReturnPersons() throws Exception {
        String query = "http://localhost:8080/api/people/?search=anakin";

        List<Person> persons = List.of(Person.builder()
                .name("Anakin Skywalker")
                .url("http://localhost:8080/api/people/11/")
                .build());
        String json = mapper.writeValueAsString(persons);
        this.mockServer
                .expect(requestTo(query))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        List<Person> result = consumer.getPersons(query);
        assertEquals(persons.get(0).getName(), result.get(0).getName());
        assertEquals(persons.get(0).getUrl(), result.get(0).getUrl());
    }

    @Test
    void getPlanetName() throws Exception {
        String query = "http://localhost:8080/api/planets/1/";
        String planetName = "Tatooine";
        String json = mapper.writeValueAsString(planetName);
        this.mockServer
                .expect(requestTo(query))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        String result = consumer.getPlanetName(query);
        assertEquals(planetName, result);
    }

    @Test
    void getMovieTitle() throws Exception {
        String query = "http://localhost:8080/api/films/1";
        String movieName = "A New Hope";
        String json = mapper.writeValueAsString(movieName);
        this.mockServer
                .expect(requestTo(query))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        String result = consumer.getMovieTitle(query);
        assertEquals(movieName, result);
    }
}
