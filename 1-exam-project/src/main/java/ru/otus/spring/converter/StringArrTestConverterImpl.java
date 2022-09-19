package ru.otus.spring.converter;

import ru.otus.spring.domain.Test;

import java.util.ArrayList;
import java.util.List;

public class StringArrTestConverterImpl implements StringArrTestConverter {
    @Override
    public Test convert(String[] dataArray) {
        Test test = new Test();
        String question = dataArray[0];
        test.setQuestion(question);
        List<String> options = new ArrayList<>();
        for (int i = 1; i < dataArray.length; i++) {
            options.add(dataArray[i]);
        }
        test.setOptions(options);

        return test;
    }
}
