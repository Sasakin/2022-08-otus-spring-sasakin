package ru.otus.spring.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.dao.BookDao;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.rest.controller.BookController;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.rest.controller.dto.BookDto;
import ru.otus.spring.book.rest.controller.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    /*@Autowired
    private MockMvc mvc;

    @MockBean
    private BookDao bookRepository;

    @MockBean
    private AuthorDao authorRepository;

    @MockBean
    private GenreDao genreRepository;

    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Fantasy");

        List<Book> books = List.of(new Book("Silent hill", author, genre), new Book("Silent hill2", author, genre));
        given(bookRepository.findAll()).willReturn(books);

        AuthorDto authorDto = AuthorDto.toDto(author);
        GenreDto genreDto = GenreDto.toDto(genre);
        BookDto bookDto = BookDto.toDto(books.get(0), authorDto, genreDto);

        var r = mvc.perform(get("/api/book/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(List.of(bookDto))));
    }

    @Test
    void shouldReturnCorrectBooksListWithSearch() throws Exception {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Fantasy");

        List<Book> expectedBooks = List.of(new Book(2L,"Other book", author, genre, new ArrayList<>()));

        List<Book> books = List.of(new Book(1L, "Silent hill", author, genre, new ArrayList<>()), expectedBooks.get(0));
        given(bookRepository.findAll()).willReturn(books);

        AuthorDto authorDto = AuthorDto.toDto(author);
        GenreDto genreDto = GenreDto.toDto(genre);
        BookDto bookDto = BookDto.toDto(expectedBooks.get(0), authorDto, genreDto);

        mvc.perform(get("api/book/search").param("keyword","Other"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(List.of(bookDto))));
    }

    @Test
    void testEdit() throws Exception {
        Author author = new Author(1L, "Ivan");
        Genre genre = new Genre(1L, "Fantasy");

        Book book = new Book(1L, "Silent hill", author, genre, new ArrayList<>());
        List<Author> expectedAuthors = List.of(author);
        List<Genre> expectedGenres = List.of(genre);


        given(bookRepository.findById(1L)).willReturn(Optional.of(book));
        given(authorRepository.findAll()).willReturn(expectedAuthors);
        given(genreRepository.findAll()).willReturn(expectedGenres);

        AuthorDto authorDto = AuthorDto.toDto(author);
        GenreDto genreDto = GenreDto.toDto(genre);
        BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);

        var expectedResponse = new EditBookDataResponse(bookDto,
                expectedAuthors.stream().map(a -> AuthorDto.toDto(a)).collect(Collectors.toList()),
                expectedGenres.stream().map(g -> GenreDto.toDto(g)).collect(Collectors.toList()))

        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(expectedResponse)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

}