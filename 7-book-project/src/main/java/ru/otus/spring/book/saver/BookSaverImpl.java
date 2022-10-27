package ru.otus.spring.book.saver;

import org.springframework.stereotype.Component;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.AuthorService;
import ru.otus.spring.book.services.BookService;

import javax.transaction.Transactional;


@Component
public class BookSaverImpl implements BookSaver {

    private BookService bookService;

    private AuthorService authorService;

    private GenreDao genreService;


    private String authorName;

    private String genreTitle;

    private String bookTitle;

    private String[] comments;

    public BookSaverImpl(BookService bookService, AuthorService authorService, GenreDao genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public BookSaver authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    @Override
    public BookSaver genreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
        return this;
    }

    @Override
    public BookSaver bookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    @Override
    public BookSaver bookComments(String... comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public void save() {

        Author author = authorService.getByName(authorName);
        if(author == null) {
            author = new Author(null, authorName);
            //authorService.insert(author);
        }

        Genre genre = genreService.getByTitle(genreTitle);
        if(genre == null) {
            genre = new Genre(null, genreTitle);
            //genreService.insert(genre);
        }

        Book book = new Book(bookTitle, author, genre);

        for(String comment : comments) {
            Comment comment1 = new Comment(comment, book);
            book.getComments().add(comment1);
        }

        bookService.save(book);

    }
}
