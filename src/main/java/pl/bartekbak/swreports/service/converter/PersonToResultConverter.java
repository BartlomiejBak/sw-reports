package pl.bartekbak.swreports.service.converter;

import pl.bartekbak.swreports.dto.Person;
import pl.bartekbak.swreports.dto.Result;

import java.util.List;

public interface PersonToResultConverter {
    List<Result> convertToResultList(Person person);
}
