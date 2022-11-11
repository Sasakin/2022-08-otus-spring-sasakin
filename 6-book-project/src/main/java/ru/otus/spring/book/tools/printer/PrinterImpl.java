package ru.otus.spring.book.tools.printer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.AuthorService;
import ru.otus.spring.book.services.BookService;
import ru.otus.spring.book.services.GenreService;

import java.util.List;


@AllArgsConstructor
@Component
public class PrinterImpl implements Printer {

    private BookService bookService;

    private AuthorService authorService;

    private GenreService genreService;


    @Override
    @Transactional(readOnly = true)
    public void printBooks() {
        List<Book> bookList = bookService.getAll();

        bookList.forEach(System.out :: println);
    }

    @Override
    public void printAuthors() {
        List<Author> authors = authorService.getAll();

        authors.forEach(System.out :: println);
    }

    @Override
    public void printGenres() {
        List<Genre> genres = genreService.getAll();

        genres.forEach(System.out :: println);
    }

    @Override
    @Transactional(readOnly = true)
    public void printCommentsByBookId(Long bookId) {
        Book book = bookService.getById(bookId);
        if(book == null) {
            System.out.println("Book with id=" + bookId + " + not found");
            return;
        }
        List<Comment> comments = book.getComments();
        comments.forEach(System.out :: println);
    }
}
