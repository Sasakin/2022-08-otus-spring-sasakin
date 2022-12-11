package ru.otus.spring.book.rest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.book.domain.Genre;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GenreDto  implements Serializable {

    private Long id;

    private String title;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getTitle());
    }

    public static Genre toGenre(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getTitle());
    }

}
