package ru.otus.spring.book.hystrix.services;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.rest.controller.NotFoundException;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.rest.controller.dto.BookDto;
import ru.otus.spring.book.rest.controller.dto.GenreDto;
import ru.otus.spring.book.services.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HystrixBookServiceImpl implements HystrixBookService {

    private final BookService bookService;

    @Override
    @HystrixCommand(commandKey="getBookList", fallbackMethod="buildFallbackBookList")
    public List<BookDto> getBookList() {
        List<Book> books = bookService.getAll();
        List<BookDto> resultList = books.stream().map(book -> {
            AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
            GenreDto genreDto = GenreDto.toDto(book.getGenre());
            BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);
            return bookDto;
        }).collect(Collectors.toList());

        return resultList;
    }

    @Override
    @HystrixCommand(commandKey="getBook", fallbackMethod="buildFallbackBook")
    public BookDto getBook(Long id) {
        Book book = bookService.getById(id).orElseThrow(NotFoundException::new);
        AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
        GenreDto genreDto = GenreDto.toDto(book.getGenre());
        BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);

        return bookDto;
    }

    @Override
    @HystrixCommand(commandKey="saveBook", fallbackMethod="buildFallbackResponseEntityVoid")
    public ResponseEntity<Void> saveBook(BookDto bookDto) {
        Author author = AuthorDto.toAuthor(bookDto.getAuthor());
        Genre genre = GenreDto.toGenre(bookDto.getGenre());
        Book book = BookDto.toBook(bookDto, author, genre);
        bookService.save(book);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    @HystrixCommand(commandKey="deleteBook", fallbackMethod="buildFallbackResponseEntityVoid")
    public ResponseEntity<Void> deleteBook(Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    @HystrixCommand(commandKey="searchBooksByKeyWord", fallbackMethod="buildFallbackBookList")
    public List<BookDto> searchBooksByKeyWord(String keyword) {
        List<Book> books = bookService.getAll();
        List<BookDto> bookListFiltered = books.stream()
                .filter(book -> book.getTitle() != null
                        && book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .limit(15l)
                .map(book -> {
                    AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
                    GenreDto genreDto = GenreDto.toDto(book.getGenre());
                    BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);
                    return bookDto;
                })
                .collect(Collectors.toList());

        return bookListFiltered;
    }

    public List<BookDto> buildFallbackBookList() {
        AuthorDto authorDto = new AuthorDto(0L, "N/A");
        GenreDto genreDto = new GenreDto(0L,"N/A");
        BookDto bookDto = new BookDto(0L, "N/A", authorDto, genreDto);

        List<BookDto> bookList = new ArrayList<>();
        bookList.add(bookDto);
        return bookList;
    }

    public BookDto buildFallbackBookList(Long id) {
        AuthorDto authorDto = new AuthorDto(0L, "N/A");
        GenreDto genreDto = new GenreDto(0L,"N/A");
        BookDto bookDto = new BookDto(0L, "N/A", authorDto, genreDto);

        return bookDto;
    }

    public ResponseEntity<Void> buildFallbackResponseEntityVoid(BookDto bookDto) {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity<Void> buildFallbackResponseEntityVoid(Long id) {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
