package ru.otus.spring.domain;

import lombok.Data;

import java.util.List;

@Data
public class Test {

    private String question;

    private List<String> options;

    private Integer answerNumberForCheck;

}
