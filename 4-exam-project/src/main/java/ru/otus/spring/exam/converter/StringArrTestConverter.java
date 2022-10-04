package ru.otus.spring.exam.converter;

import ru.otus.spring.exam.domain.Test;

public interface StringArrTestConverter {
    Test convert(String[] arr);
}
