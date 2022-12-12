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
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.dao.BookDao;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.rest.controller.dto.BookDto;
import ru.otus.spring.book.rest.controller.dto.GenreDto;
import ru.otus.spring.book.services.SequenceGeneratorService;

@RestController()
@RequiredArgsConstructor
public class BookController {

    private final SequenceGeneratorService sequenceGeneratorService;

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @GetMapping(value = {"/api/book/list"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookDto> listPage() {
        Flux<BookDto> books = bookDao.findAll().map(book -> {
            AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
            GenreDto genreDto = GenreDto.toDto(book.getGenre());
            BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);
            return bookDto;
        });

        return books;
    }

    @GetMapping(value = {"/api/book/search"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookDto> listPageWithSearch(@ModelAttribute("keyword") String keyword) {
        return bookDao.findAll()
                .filter(book -> book.getTitle() != null
                        && book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .map(book -> {
                    AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
                    GenreDto genreDto = GenreDto.toDto(book.getGenre());
                    BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);
                    return bookDto;
                }).limitRate(15);
    }

    @GetMapping(value = {"/api/book"})
    public Mono<BookDto> editPage(@RequestParam("bookId") long id) {
        Mono<BookDto> bookMono = bookDao.findById(id).map(book -> {
            AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
            GenreDto genreDto = GenreDto.toDto(book.getGenre());
            BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);
            return bookDto;
        });

        return bookMono;
    }

    @PostMapping("/api/book/save")
    @Transactional
    public Mono<Book> saveBook(@RequestBody BookDto bookDto) {
        Author author = AuthorDto.toAuthor(bookDto.getAuthor());
        Genre genre = GenreDto.toGenre(bookDto.getGenre());
        final Book book = BookDto.toBook(bookDto, author, genre);

        if (bookDto.getId() == null) {
            Mono<Book> bookMono = genereateBookDataForSave(author, genre, book);

            Mono<Book> resultBookMono =
                    Mono.zip(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME),
                    bookMono,
                    (id, book1) -> {
                        book1.setId(id);
                        return book;
                    });
            return resultBookMono;//.map(bMono -> new ResponseEntity<>(HttpStatus.OK));

        }

        Mono<Book> bookMono = genereateBookDataForSave(author, genre, book);

        return bookMono;//.map(bMono -> new ResponseEntity<>(HttpStatus.OK));
    }

    private Mono<Book> genereateBookDataForSave(Author author, Genre genre, Book book) {
        return Mono.zip(author.getId() == null ?
                        Mono.zip(authorDao.getByName(author.getName()),
                                sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME),
                                (author1, id) -> {
                                    if(author1 == null) {
                                        author.setId(id);
                                        return author;
                                    }
                                    return author1;
                                }) :
                        Mono.just(author),
                genre.getId() == null ?
                        Mono.zip(genreDao.getByTitle(genre.getTitle()),
                                sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME),
                                (genre1, id) -> {
                                    if(genre1 == null) {
                                        genre.setId(id);
                                        return genre;
                                    }
                                    return genre1;
                                }) :
                        Mono.just(genre),
                (author1, genre1) -> {
                    book.setAuthor(author1);
                    book.setGenre(genre1);
                    return book;
                });
    }

    @GetMapping("/api/book/delete")
    public Mono<ResponseEntity<Void>> deleteBook(@RequestParam("id") long id) {
        return bookDao.deleteById(id).map(bMono -> new ResponseEntity<>(HttpStatus.OK));
    }
}
