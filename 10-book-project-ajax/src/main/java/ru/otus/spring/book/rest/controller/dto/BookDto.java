package ru.otus.spring.book.rest.controller.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.book.domain.Book;

@Data
@AllArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

    public static BookDto toDto(@NotNull Book book, AuthorDto author, GenreDto genre) {
        return new BookDto(book.getId(), book.getTitle(), author, genre);
    }

}
