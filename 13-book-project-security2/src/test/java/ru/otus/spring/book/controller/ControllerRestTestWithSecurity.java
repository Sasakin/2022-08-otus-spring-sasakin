package ru.otus.spring.book.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.rest.controller.BookController;
import ru.otus.spring.book.security.error.ExtAccessDeniedHandler;
import ru.otus.spring.book.services.BookService;
import ru.otus.spring.book.services.UserService;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import({ExtAccessDeniedHandler.class})
public class ControllerRestTestWithSecurity {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testIfNotAccess_shouldRedirectToLogin() throws Exception {
        mvc.perform(get("/api/book/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"));

        mvc.perform(get("/api/book/search").param("keyword","Other").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"));

        mvc.perform(get("/api/book/").param("id", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"));

    }


    @WithMockUser(username = "user", password = "password", authorities = {"USER"})
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/api/book/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/book/search").param("keyword","Other").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        Author author = new Author(1L, "Ivan");
        Genre genre = new Genre(1L, "Fantasy");
        Book book = new Book(1L, "Silent hill", author, genre, new ArrayList<>());
        given(bookService.getById(1l)).willReturn(Optional.of(book));
        mvc.perform(get("/api/book/").param("bookId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
