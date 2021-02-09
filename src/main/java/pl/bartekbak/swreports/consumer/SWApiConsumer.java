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
import pl.bartekbak.swreports.exception.QueryProcessingException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SWApiConsumer {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Person> getPersonList(String query) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Person> personList = new ArrayList<>();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<Person>>() {
        });

        try {
            String requestUri = query;
            boolean hasNext = true;
            while (hasNext) {
                ResponseEntity<String> response = getObject(requestUri);
                JsonNode root = mapper.readTree(response.getBody());
                List<Person> partialList = reader.readValue(root.get("results"));
                personList.addAll(partialList);

                if (root.get("next").isNull()) {
                    hasNext = false;
                } else {
                    requestUri = root.get("next").asText();
                }
            }
            return personList;
        } catch (Exception e) {
            throw new QueryProcessingException(e.getMessage());
        }

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

    private ResponseEntity<String> getObject(String query) {
        return restTemplate.getForEntity(query, String.class);
    }
}
