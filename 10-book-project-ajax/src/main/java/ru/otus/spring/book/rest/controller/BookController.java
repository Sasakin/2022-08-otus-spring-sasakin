package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.rest.controller.dto.BookDto;
import ru.otus.spring.book.rest.controller.dto.GenreDto;
import ru.otus.spring.book.rest.controller.response.EditBookDataResponse;
import ru.otus.spring.book.services.AuthorService;
import ru.otus.spring.book.services.BookService;
import ru.otus.spring.book.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping({"/list"})
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

    @GetMapping("/search")
    public List<BookDto> listPageWithSearch(@ModelAttribute("keyword") String keyword, Model model) {
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

   @GetMapping("/edit")
    public EditBookDataResponse editPage(@RequestParam("id") long id) {
        Book book = bookService.getById(id).orElseThrow(NotFoundException::new);
        AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
        GenreDto genreDto = GenreDto.toDto(book.getGenre());
        BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);

        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();

        return new EditBookDataResponse(bookDto,
                authors.stream().map(author -> AuthorDto.toDto(author)).collect(Collectors.toList()),
                genres.stream().map(genre -> GenreDto.toDto(genre)).collect(Collectors.toList()));
    }

    @GetMapping("/add")
    public EditBookDataResponse getAddData() {

        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();

        return new EditBookDataResponse(new BookDto(),
                authors.stream().map(author -> AuthorDto.toDto(author)).collect(Collectors.toList()),
                genres.stream().map(genre -> GenreDto.toDto(genre)).collect(Collectors.toList()));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveBook(@RequestBody BookDto bookDto, RedirectAttributes ra) {
        Author author = AuthorDto.toAuthor(bookDto.getAuthor());
        Genre genre = GenreDto.toGenre(bookDto.getGenre());
        Book book = BookDto.toBook(bookDto, author, genre);
        bookService.save(book);
        ra.addFlashAttribute("message", "The book has been saved successfully.");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
