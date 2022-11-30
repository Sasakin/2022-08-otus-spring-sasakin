package ru.otus.spring.book.rest.controller.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.book.domain.Genre;

@Data
@AllArgsConstructor
public class GenreDto {

    private Long id;

    private String title;

    public static GenreDto toDto(@NotNull Genre genre) {
        return new GenreDto(genre.getId(), genre.getTitle());
    }
}
