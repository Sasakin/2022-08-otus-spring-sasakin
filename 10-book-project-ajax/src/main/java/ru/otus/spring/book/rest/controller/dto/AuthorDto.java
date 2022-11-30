package ru.otus.spring.book.rest.controller.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.book.domain.Author;

@Data
@AllArgsConstructor
public class AuthorDto {
    private Long id;

    private String name;

    public static AuthorDto toDto(@NotNull Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
