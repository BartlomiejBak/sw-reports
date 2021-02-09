package pl.bartekbak.swreports.consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(MockitoExtension.class)
class SWApiConsumerTest {

    private MockRestServiceServer mockServer;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    SWApiConsumer consumer;


    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getPersonList() {
        String url = "http://localhost:8080/api/people/?search=anakin";

        //region person Json response
        String personResponse = "{\"count\":1,\"next\":null,\"previous\":null,\"results\":" +
                "[{\"name\":\"Anakin Skywalker\",\"height\":\"188\",\"mass\":\"84\",\"hair_color\":\"blond\"," +
                "\"skin_color\":\"fair\",\"eye_color\":\"blue\",\"birth_year\":\"41.9BBY\",\"gender\":\"male\"," +
                "\"homeworld\":\"http://localhost:8080/api/planets/1/\",\"films\":[\"http://localhost:8080/api/films/4/\"," +
                "\"http://localhost:8080/api/films/5/\",\"http://localhost:8080/api/films/6/\"],\"species\":[]," +
                "\"vehicles\":[\"http://localhost:8080/api/vehicles/44/\",\"http://localhost:8080/api/vehicles/46/\"]," +
                "\"starships\":[\"http://localhost:8080/api/starships/39/\",\"http://localhost:8080/api/starships/59/\"," +
                "\"http://localhost:8080/api/starships/65/\"],\"created\":\"2014-12-10T16:20:44.310000Z\"," +
                "\"edited\":\"2014-12-20T21:17:50.327000Z\",\"url\":\"http://localhost:8080/api/people/11/\"}]}";
        //endregion
        mockServer.expect(ExpectedCount.once(),
                requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(personResponse));
        consumer.getPersonList(url);
    }

    @Test
    void getPlanetName() {
        String url = "http://localhost:8080/api/planets/1";
        //region planet Json response
        String planetResponse = "{\"name\":\"Tatooine\",\"rotation_period\":\"23\",\"orbital_period\":\"304\"," +
                "\"diameter\":\"10465\",\"climate\":\"arid\",\"gravity\":\"1 standard\",\"terrain\":\"desert\"," +
                "\"surface_water\":\"1\",\"population\":\"200000\",\"residents\":[\"http://localhost:8080/api/people/1/\"," +
                "\"http://localhost:8080/api/people/2/\",\"http://localhost:8080/api/people/4/\"," +
                "\"http://localhost:8080/api/people/6/\",\"http://localhost:8080/api/people/7/\"," +
                "\"http://localhost:8080/api/people/8/\",\"http://localhost:8080/api/people/9/\"," +
                "\"http://localhost:8080/api/people/11/\",\"http://localhost:8080/api/people/43/\"," +
                "\"http://localhost:8080/api/people/62/\"],\"films\":[\"http://localhost:8080/api/films/1/\"," +
                "\"http://localhost:8080/api/films/3/\",\"http://localhost:8080/api/films/4/\"," +
                "\"http://localhost:8080/api/films/5/\",\"http://localhost:8080/api/films/6/\"]," +
                "\"created\":\"2014-12-09T13:50:49.641000Z\",\"edited\":\"2014-12-20T20:58:18.411000Z\"," +
                "\"url\":\"http://localhost:8080/api/planets/1/\"}";
        //endregion
        mockServer.expect(ExpectedCount.once(),
                requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(planetResponse));
        consumer.getPlanetName(url);
    }

    @Test
    void getMovieTitle() {
        String url = "http://localhost:8080/api/films/1";
        //region film Json response
        String filmResponse = "{\"title\":\"A New Hope\",\"episode_id\":4,\"opening_crawl\":\"It is a period of" +
                " civil war.\\r\\nRebel spaceships, striking\\r\\nfrom a hidden base, have won\\r\\ntheir first victory" +
                " against\\r\\nthe evil Galactic Empire.\\r\\n\\r\\nDuring the battle, Rebel\\r\\nspies managed to steal" +
                " secret\\r\\nplans to the Empire's\\r\\nultimate weapon, the DEATH\\r\\nSTAR, an armored " +
                "space\\r\\nstation with enough power\\r\\nto destroy an entire planet.\\r\\n\\r\\nPursued by the " +
                "Empire's\\r\\nsinister agents, Princess\\r\\nLeia races home aboard her\\r\\nstarship, custodian of " +
                "the\\r\\nstolen plans that can save her\\r\\npeople and restore\\r\\nfreedom to the galaxy....\"," +
                "\"director\":\"George Lucas\",\"producer\":\"Gary Kurtz, Rick McCallum\",\"release_date\":" +
                "\"1977-05-25\",\"characters\":[\"http://localhost:8080/api/people/1/\"," +
                "\"http://localhost:8080/api/people/2/\",\"http://localhost:8080/api/people/3/\"," +
                "\"http://localhost:8080/api/people/4/\",\"http://localhost:8080/api/people/5/\"," +
                "\"http://localhost:8080/api/people/6/\",\"http://localhost:8080/api/people/7/\"," +
                "\"http://localhost:8080/api/people/8/\",\"http://localhost:8080/api/people/9/\"," +
                "\"http://localhost:8080/api/people/10/\",\"http://localhost:8080/api/people/12/\"," +
                "\"http://localhost:8080/api/people/13/\",\"http://localhost:8080/api/people/14/\"," +
                "\"http://localhost:8080/api/people/15/\",\"http://localhost:8080/api/people/16/\"," +
                "\"http://localhost:8080/api/people/18/\",\"http://localhost:8080/api/people/19/\"," +
                "\"http://localhost:8080/api/people/81/\"],\"planets\":[\"http://localhost:8080/api/planets/1/\"," +
                "\"http://localhost:8080/api/planets/2/\",\"http://localhost:8080/api/planets/3/\"],\"starships\":" +
                "[\"http://localhost:8080/api/starships/2/\",\"http://localhost:8080/api/starships/3/\"," +
                "\"http://localhost:8080/api/starships/5/\",\"http://localhost:8080/api/starships/9/\"," +
                "\"http://localhost:8080/api/starships/10/\",\"http://localhost:8080/api/starships/11/\"," +
                "\"http://localhost:8080/api/starships/12/\",\"http://localhost:8080/api/starships/13/\"]," +
                "\"vehicles\":[\"http://localhost:8080/api/vehicles/4/\",\"http://localhost:8080/api/vehicles/6/\"," +
                "\"http://localhost:8080/api/vehicles/7/\",\"http://localhost:8080/api/vehicles/8/\"]," +
                "\"species\":[\"http://localhost:8080/api/species/1/\",\"http://localhost:8080/api/species/2/\"," +
                "\"http://localhost:8080/api/species/3/\",\"http://localhost:8080/api/species/4/\"," +
                "\"http://localhost:8080/api/species/5/\"],\"created\":\"2014-12-10T14:23:31.880000Z\",\"edited\":" +
                "\"2014-12-20T19:49:45.256000Z\",\"url\":\"http://localhost:8080/api/films/1/\"}";
        //endregion
        mockServer.expect(ExpectedCount.once(),
                requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(filmResponse));
        consumer.getMovieTitle(url);
    }
}
