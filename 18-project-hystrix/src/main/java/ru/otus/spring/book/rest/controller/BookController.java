package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.hystrix.services.HystrixBookService;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.rest.controller.dto.BookDto;
import ru.otus.spring.book.rest.controller.dto.GenreDto;
import ru.otus.spring.book.services.BookService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
public class BookController {

    private final HystrixBookService bookService;

    @GetMapping({"/api/book/list"})
    public List<BookDto> listPage() {
        return bookService.getBookList();
    }

    @GetMapping("/api/book/search")
    public List<BookDto> listPageWithSearch(@ModelAttribute("keyword") String keyword) {
        return bookService.searchBooksByKeyWord(keyword);
    }

   @GetMapping("/api/book/")
    public BookDto editPage(@RequestParam("bookId") long id) {
        return bookService.getBook(id);
    }

    @PostMapping("/api/book/save")
    public ResponseEntity<Void> saveBook(@RequestBody BookDto bookDto) {
        return bookService.saveBook(bookDto);
    }

    @GetMapping("/api/book/delete")
    public ResponseEntity<Void> deleteBook(@RequestParam("id") long id) {
        return bookService.deleteBook(id);
    }
}
