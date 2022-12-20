package ru.otus.spring.book.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private SequenceGeneratorService sequenceGeneratorService;

    private BookDao bookDao;

    private AuthorDao authorDao;

    private GenreDao genreDao;

    @Override
    @Transactional
    public Mono<Book> save(BookDto bookDto) {
        Author author = AuthorDto.toAuthor(bookDto.getAuthor());
        Genre genre = GenreDto.toGenre(bookDto.getGenre());
        final Book book = BookDto.toBook(bookDto, author, genre);

        if (book.getId() != null) {
            return buildSaveMonoBookWithoutId(book, author.getName(), genre.getTitle());
        }

        return buildSaveMonoBook(book, author.getName(), genre.getTitle());
    }

    @Override
    public Mono<BookDto> getById(long id) {
        return bookDao.findById(id).map(book -> {
            AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
            GenreDto genreDto = GenreDto.toDto(book.getGenre());
            BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);
            return bookDto;
        });
    }

    @Override
    public Flux<BookDto> getAll() {
        return bookDao.findAll().flatMap(book -> {
            return Mono.zip(authorDao.findById(book.getAuthorId()),
                            Mono.just(book),
                            ((author, book1) -> {
                                book1.setAuthor(author);
                                return book1;
                            }))
                    .zipWith(genreDao.findById(book.getGenreId()),
                            (book1, genre1) -> {
                                book1.setGenre(genre1);
                                return book1;
                            });
        }).map(book -> {
            AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());
            GenreDto genreDto = GenreDto.toDto(book.getGenre());
            BookDto bookDto = BookDto.toDto(book, authorDto, genreDto);
            return bookDto;
        });
    }

    @Override
    public Flux<BookDto> searchByKeyWord(String keyword) {
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

    private Mono<Book> buildSaveMonoBook(Book book, String authorName, String genreTitle) {
        Mono<Book> beforeSave = buildMonoBookAuthorAndGenre(book, authorName, genreTitle)
                .zipWith(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME),
                        (book1, id) -> {
                            book1.setId(id);
                            return book1;
                        });
        return buildMonoSaveFunction(beforeSave);
    }

    private Mono<Book> buildSaveMonoBookWithoutId(Book book, String authorName, String genreTitle) {
        Mono<Book> beforeSave = buildMonoBookAuthorAndGenre(book, authorName, genreTitle);
        return buildMonoSaveFunction(beforeSave);
    }

    private Mono<Book> buildMonoSaveFunction(Mono<Book> beforeSave) {
        return beforeSave.flatMap(book -> bookDao.save(book));
    }

    private Mono<Book> buildMonoBookAuthorAndGenre(Book book, String authorName, String genreTitle) {
        return Mono.zip(getAuthorOrSave(authorName),
                        Mono.just(book),
                        ((author, book1) -> {
                            book1.setAuthor(author);
                            return book1;
                        }))
                .zipWith(getGenreOrSave(genreTitle),
                        (book1, genre1) -> {
                            book1.setGenre(genre1);
                            return book1;
                        });
    }

    /**
     * Проверить наличие автора в БД
     *
     * @param name - имя автора
     * @return
     */
    public Mono<Author> getAuthorOrSave(String name) {
        return authorDao.getByName(name)
                .switchIfEmpty(Mono.zip(Mono.just(name),
                                sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME),
                                (authorName, id) -> new Author(id, name))
                        .delayUntil(author -> authorDao.save(author)))
                .single();
    }

    /**
     * Проверить наличие жанра в БД
     *
     * @param title - имя жанра
     * @return
     */
    public Mono<Genre> getGenreOrSave(String title) {
        return genreDao.getByTitle(title)
                .switchIfEmpty(Mono.zip(Mono.just(title),
                                sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME),
                                (genreTitle, id) -> new Genre(id, genreTitle))
                        .delayUntil(genre -> genreDao.save(genre)))
                .single();
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(long id) {
        return bookDao.deleteById(id);
    }
}
