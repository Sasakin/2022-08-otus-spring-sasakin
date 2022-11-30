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
import ru.otus.spring.book.services.AuthorServiceImpl;
import ru.otus.spring.book.services.BookServiceImpl;
import ru.otus.spring.book.services.GenreServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import({BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class})
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

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

        mvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", books));
    }

    @Test
    void shouldReturnCorrectBooksListWithSearch() throws Exception {
        Author author = new Author(null, "Ivan");
        Genre genre = new Genre(null, "Fantasy");

        List<Book> expectedBooks = List.of(new Book(2L,"Other book", author, genre, new ArrayList<>()));

        List<Book> books = List.of(new Book(1L, "Silent hill", author, genre, new ArrayList<>()), expectedBooks.get(0));
        given(bookRepository.findAll()).willReturn(books);

        mvc.perform(get("/book/search").param("keyword","Other")) //?keyword="+"Other"
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", expectedBooks));
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

        mvc.perform(get("/book/edit").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book",book))
                .andExpect(model().attribute("authors",expectedAuthors))
                .andExpect(model().attribute("genres", expectedGenres))
                .andExpect(model().attribute("title", "Edit book"));
    }

}