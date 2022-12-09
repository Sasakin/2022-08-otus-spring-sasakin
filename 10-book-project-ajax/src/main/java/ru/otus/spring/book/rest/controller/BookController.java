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
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.rest.controller.dto.BookDto;
import ru.otus.spring.book.rest.controller.dto.GenreDto;
import ru.otus.spring.book.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping({"/api/book/list"})
    public List<BookDto> listPage() {
        List<Book> books = bookService.getAll();
        List<BookDto> resultList = books.stream().map(book -> {
            AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
            GenreDto genreDto = GenreDto.toDto(book.getGenre());
            BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);
            return bookDto;
        }).collect(Collectors.toList());

        return resultList;
    }

    @GetMapping("/api/book/search")
    public List<BookDto> listPageWithSearch(@ModelAttribute("keyword") String keyword) {
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

   @GetMapping("/api/book/")
    public BookDto editPage(@RequestParam("bookId") long id) {
        Book book = bookService.getById(id).orElseThrow(NotFoundException::new);
        AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
        GenreDto genreDto = GenreDto.toDto(book.getGenre());
        BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);

        return bookDto;
    }

    @PostMapping("/api/book/save")
    public ResponseEntity<Void> saveBook(@RequestBody BookDto bookDto) {
        Author author = AuthorDto.toAuthor(bookDto.getAuthor());
        Genre genre = GenreDto.toGenre(bookDto.getGenre());
        Book book = BookDto.toBook(bookDto, author, genre);
        bookService.save(book);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/api/book/delete")
    public ResponseEntity<Void> deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
