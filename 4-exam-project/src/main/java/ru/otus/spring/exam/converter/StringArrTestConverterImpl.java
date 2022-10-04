package ru.otus.spring.exam.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.exam.domain.Test;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringArrTestConverterImpl implements StringArrTestConverter {
    @Override
    public Test convert(String[] dataArray) {
        Test test = new Test();
        String question = dataArray[0];
        test.setQuestion(question);
        List<String> options = new ArrayList<>();
        for (int i = 1; i < dataArray.length - 1; i++) {
            options.add(dataArray[i]);
        }
        test.setOptions(options);

        test.setAnswerNumberForCheck(Integer.valueOf(dataArray[dataArray.length - 1]));

        return test;
    }
}
