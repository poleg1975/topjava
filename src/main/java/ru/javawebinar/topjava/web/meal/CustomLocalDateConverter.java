package ru.javawebinar.topjava.web.meal;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.Date;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public class CustomLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        return parseLocalDate(s);
    }
}
