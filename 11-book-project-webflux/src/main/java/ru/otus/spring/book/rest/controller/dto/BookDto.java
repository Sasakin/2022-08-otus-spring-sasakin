package ru.otus.spring.book.rest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {

    private Long id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

    public static BookDto toDto(Book book, AuthorDto author, GenreDto genre) {
        return new BookDto(book.getId(), book.getTitle(), author, genre);
    }

    public static Book toBook(BookDto bookDto, Author author, Genre genre) {
        return new Book(bookDto.getId(), bookDto.getTitle(), author, genre);
    }

}
