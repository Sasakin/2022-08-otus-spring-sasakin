package ru.otus.spring.converter;

import ru.otus.spring.domain.Test;

public interface StringArrTestConverter {
    Test convert(String[] arr);
}
