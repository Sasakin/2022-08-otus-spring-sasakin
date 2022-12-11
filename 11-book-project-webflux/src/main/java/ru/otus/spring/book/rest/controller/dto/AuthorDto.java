package ru.otus.spring.book.rest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.book.domain.Author;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthorDto  implements Serializable {
    private Long id;

    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

    public static Author toAuthor(AuthorDto author) {
        return new Author(author.getId(), author.getName());
    }
}
