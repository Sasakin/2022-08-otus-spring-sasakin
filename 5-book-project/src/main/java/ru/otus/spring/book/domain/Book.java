package ru.otus.spring.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Book {
    private Long id;

    private String title;

    private Author author;

    private Genre genre;
}
