package ru.otus.spring.book.rest.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.rest.controller.dto.BookDto;
import ru.otus.spring.book.rest.controller.dto.GenreDto;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class EditBookDataResponse implements Serializable {

    private BookDto book;

    private List<AuthorDto> authors;

    private List<GenreDto> genres;

}
