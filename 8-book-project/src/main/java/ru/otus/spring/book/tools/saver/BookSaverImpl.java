package ru.otus.spring.book.tools.saver;

import org.springframework.stereotype.Component;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.BookService;


@Component
public class BookSaverImpl implements BookSaver {

    private BookService bookService;

    public BookSaverImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void save(String authorName, String genreTitle, String bookTitle, String... comments) {

        Author author = new Author(authorName);

        Genre genre = new Genre(genreTitle);

        Book book = new Book(bookTitle, author, genre);

        for(String comment : comments) {
            Comment comment1 = new Comment(comment);
            book.getComments().add(comment1);
        }

        bookService.save(book);

    }
}
