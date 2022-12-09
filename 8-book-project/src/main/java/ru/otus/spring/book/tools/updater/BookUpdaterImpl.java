package ru.otus.spring.book.tools.updater;

import org.springframework.stereotype.Component;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.services.BookService;

@Component
public class BookUpdaterImpl implements  BookUpdater {

    private BookService bookService;

    public BookUpdaterImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void update(Long bookId, String... comments) {
        Book book = bookService.getById(bookId);

        if(book == null) {
            return;
        }

        for(String comment : comments) {
            Comment comment1 = new Comment(comment);
            book.getComments().add(comment1);
        }

        bookService.save(book);
    }
}
