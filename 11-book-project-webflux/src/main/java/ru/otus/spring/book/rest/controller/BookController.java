package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

import java.util.HashSet;

@RestController()
@RequiredArgsConstructor
public class BookController {

    private final SequenceGeneratorService sequenceGeneratorService;

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @GetMapping(value = {"/api/book/list"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookDto> listPage() {
        Flux<BookDto> books = mongoOperations.findAll(Book.class).map(book -> {
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

        if(book.getId() != null) {
            return buildSaveMonoBookWithoutId(book, author.getName(), genre.getTitle());
        }

        return buildSaveMonoBook(book, author.getName(), genre.getTitle());
    }

    private final ReactiveMongoOperations mongoOperations;

    private Mono<Book> buildSaveMonoBook(Book book, String authorName, String genreTitle) {
        return buildMonoBookAuthorAndGenre(book, authorName, genreTitle)
                .zipWith(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME),
                        (book1, id) -> {
                            book1.setId(id);
                            return book1;
                        })
                .delayUntil(book1 -> mongoOperations.save(book1));
                //.doOnNext(book1 -> bookDao.save(book1));
                /*.doOnNext(book1 -> Mono.zip(Mono.just(book1),
                            Mono.just(book1).doOnNext(book2 -> {
                                Author author = book2.getAuthor();
                                if(author.getBooks() == null) {
                                    author.setBooks(new HashSet<>());
                                }
                                author.getBooks().add(book2);
                            }),
                            (book2, autor) -> book2))
                .doOnNext(book1 -> Mono.zip(Mono.just(book1),
                        mongoOperations.update(Genre.class)
                                .matching(Criteria.where("id").is(book1.getGenre().getId()))
                                .apply(new Update().push("books", book1)).findAndModify(),
                        (book2, genre) -> book2));*/
    }

    private Mono<Book> buildSaveMonoBookWithoutId(Book book, String authorName, String genreTitle) {
        return buildMonoBookAuthorAndGenre(book, authorName, genreTitle)
                .delayUntil(book1 -> mongoOperations.save(book1));
                /*.doOnNext(book1 -> Mono.zip(Mono.just(book1),
                        mongoOperations.update(Author.class)
                                .matching(Criteria.where("id").is(book1.getAuthor().getId()))
                                .apply(new Update().push("books", book1)).findAndModify(),
                        (book2, autor) -> book2))
                .doOnNext(book1 -> Mono.zip(Mono.just(book1),
                        mongoOperations.update(Genre.class)
                                .matching(Criteria.where("id").is(book1.getGenre().getId()))
                                .apply(new Update().push("books", book1)).findAndModify(),
                        (book2, genre) -> book2));*/
    }

    private Mono<Book> buildMonoBookAuthorAndGenre(Book book, String authorName, String genreTitle) {
        return Mono.zip(getAuthor(authorName),
                        Mono.just(book),
                        ((author, book1) -> {
                            book1.setAuthor(author);
                            return book1;
                        }))
                .zipWith(getGenre(genreTitle),
                        (book1, genre1) -> {
                            book1.setGenre(genre1);
                            return book1;
                        });
    }

    /**
     * Проверить наличие автора в БД
     * @param name - имя автора
     * @return
     */
    public Mono<Author> getAuthor(String name) {
        return mongoOperations
                .find(Query.query(Criteria.where("name").is(name)), Author.class)
                .switchIfEmpty(Mono.zip(Mono.just(name),
                                sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME),
                                (authorName, id) -> new Author(id, name))
                        .delayUntil(author -> mongoOperations.save(author)))
                .single();
    }

    /**
     * Проверить наличие жанра в БД
     * @param title - имя жанра
     * @return
     */
    public Mono<Genre> getGenre(String title) {
        return mongoOperations
                .find(Query.query(Criteria.where("title").is(title)), Genre.class)
                .switchIfEmpty(Mono.zip(Mono.just(title),
                                sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME),
                                (genreTitle, id) -> new Genre(id, genreTitle))
                        .delayUntil(genre -> mongoOperations.save(genre)))
                .single();
    }

    @GetMapping("/api/book/delete")
    public Mono<ResponseEntity<Void>> deleteBook(@RequestParam("id") long id) {
        return bookDao.deleteById(id).map(bMono -> new ResponseEntity<>(HttpStatus.OK));
    }
}
