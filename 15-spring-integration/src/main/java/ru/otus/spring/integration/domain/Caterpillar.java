package ru.otus.spring.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Caterpillar {

    private Long id;

    public String getType() {
        return "Caterpillar";
    }

}
