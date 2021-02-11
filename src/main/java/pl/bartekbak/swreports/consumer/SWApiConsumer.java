package pl.bartekbak.swreports.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Planet;
import pl.bartekbak.swreports.exception.QueryProcessingException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SWApiConsumer {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Planet> getPlanets(String query) {
        ObjectReader reader = mapper.readerFor(new TypeReference<List<Planet>>() {
        });
        return getList(query, reader);
    }

    public List<Person> getPersons(String query) {
        ObjectReader reader = mapper.readerFor(new TypeReference<List<Person>>() {
        });
        return getList(query, reader);
    }

    public String getPlanetName(String query) {
        try {
            JsonNode node = mapper.readTree(getObject(query).getBody());
            return node.get("name").asText();
        } catch (Exception e) {
            throw new QueryProcessingException(e.getMessage());
        }
    }

    public String getMovieTitle(String query) {
        try {
            JsonNode node = mapper.readTree(getObject(query).getBody());
            return node.get("title").asText();
        } catch (Exception e) {
            throw new QueryProcessingException(e.getMessage());
        }
    }

    private <T> List<T> getList(String query, ObjectReader reader) {
        List<T> list = new ArrayList<>();
        String requestUri = query;
        boolean hasNext = true;
        try {
            while (hasNext) {
                ResponseEntity<String> response = getObject(requestUri);
                JsonNode root = mapper.readTree(response.getBody());
                list.addAll(reader.readValue(root.get("results")));
                hasNext = !root.get("next").isNull();
                if (hasNext) requestUri = root.get("next").asText();
            }
            return list;
        } catch (Exception e) {
            throw new QueryProcessingException(e.getMessage());
        }
    }

    private ResponseEntity<String> getObject(String query) {
        return restTemplate.getForEntity(query, String.class);
    }
}
