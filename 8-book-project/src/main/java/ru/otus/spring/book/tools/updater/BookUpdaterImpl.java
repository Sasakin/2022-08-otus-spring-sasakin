package ru.otus.spring.book.tools.updater;

import org.springframework.stereotype.Component;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.services.BookService;

@Component
public class BookUpdaterImpl implements  BookUpdater {

    private BookService bookService;

    private Book book;

    private String[] comments;

    public BookUpdaterImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public BookUpdater addBookComments(String... comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public void update() {

        if(book == null) {
            return;
        }

        for(String comment : comments) {
            Comment comment1 = new Comment(comment, book);
            book.getComments().add(comment1);
        }

        bookService.save(book);
    }

    @Override
    public BookUpdater byId(Long bookId) {
        book = bookService.getById(bookId);

        return this;
    }
}
