package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.rest.controller.dto.BookDto;
import ru.otus.spring.book.services.BookService;

@RestController()
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping(value = {"/api/book/list"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookDto> listPage() {
        return bookService.getAll();
    }

    @GetMapping(value = {"/api/book/search"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookDto> listPageWithSearch(@ModelAttribute("keyword") String keyword) {
        return bookService.searchByKeyWord(keyword);
    }

    @GetMapping(value = {"/api/book"})
    public Mono<BookDto> editPage(@RequestParam("bookId") long id) {
        return bookService.getById(id);
    }

    @PostMapping("/api/book/save")
    @Transactional
    public Mono<Book> saveBook(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @GetMapping("/api/book/delete")
    public Mono<ResponseEntity<Void>> deleteBook(@RequestParam("id") long id) {
        return bookService.deleteById(id).map(bMono -> new ResponseEntity<>(HttpStatus.OK));
    }
}
