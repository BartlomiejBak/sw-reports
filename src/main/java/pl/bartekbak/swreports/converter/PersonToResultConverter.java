package pl.bartekbak.swreports.converter;

import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Result;

import java.util.List;

public interface PersonToResultConverter {
    public List<Result> convertToResultList(Person person);
}
