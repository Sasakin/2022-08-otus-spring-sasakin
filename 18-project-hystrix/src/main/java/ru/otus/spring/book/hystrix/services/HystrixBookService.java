package ru.otus.spring.book.hystrix.services;

import org.springframework.http.ResponseEntity;
import ru.otus.spring.book.rest.controller.dto.BookDto;

import java.util.List;

public interface HystrixBookService {
    List<BookDto> getBookList();

    BookDto getBook(Long id);

    ResponseEntity<Void> saveBook(BookDto bookDto);

    ResponseEntity<Void> deleteBook(Long id);

    List<BookDto> searchBooksByKeyWord(String keyword);
}
