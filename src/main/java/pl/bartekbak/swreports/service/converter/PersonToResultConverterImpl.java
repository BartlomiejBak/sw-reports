package pl.bartekbak.swreports.service.converter;

import org.springframework.stereotype.Service;
import pl.bartekbak.swreports.consumer.SWApiConsumer;
import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PersonToResultConverterImpl implements PersonToResultConverter {

    private final SWApiConsumer consumer;

    public PersonToResultConverterImpl(SWApiConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public List<Result> convertToResultList(Person person) {
        List<Result> results = new ArrayList<>();

        for (String film : person.getFilms()) {
            Result result = Result.builder()
                    .characterId(getIdValue(person.getUrl()))
                    .characterName(person.getName())
                    .planetId(getIdValue(person.getHomeworld()))
                    .planetName(consumer.getPlanetName(person.getHomeworld()))
                    .filmId(getIdValue(film))
                    .filmName(consumer.getMovieTitle(film))
                    .build();
            setResultId(result);
            results.add(result);
        }
        return results;
    }

    private void setResultId(Result result) {
        int id = result.getCharacterId() * 100000
                + result.getPlanetId() * 100
                + result.getFilmId();

        result.setResultId(id);
    }

    private int getIdValue(String url) {
        List<String> urlDecompose = Arrays.asList(url.split("/").clone());
        String value = urlDecompose.get(urlDecompose.size() - 1);
        return Integer.parseInt(value);
    }

}
