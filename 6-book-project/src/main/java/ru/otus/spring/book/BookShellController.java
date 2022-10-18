package ru.otus.spring.book;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.printer.Printer;
import ru.otus.spring.book.saver.BookSaver;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class BookShellController {

    private BookSaver bookSaver;

    private Printer printer;

    @ShellMethod(key = "add-book", value = "Add book to library")
    public void addBook(@ShellOption("-author")String authorName,
                        @ShellOption("-genre") String genreTitle,
                        @ShellOption("-title") String bookTitle,
                        @ShellOption("-comments") String... comments) {
        bookSaver.authorName(authorName)
                .genreTitle(genreTitle)
                .bookTitle(bookTitle)
                .bookComments(comments)
                .save();
    }

    @ShellMethod(key = "print-books", value = "Print all books")
    public void printBooks() {
        printer.printBooks();
    }

    @ShellMethod(key = "print-authors", value = "Print all authors")
    public void printAuthors() {
        printer.printAuthors();
    }

    @ShellMethod(key = "print-genres", value = "Print all genres")
    public void printGenres() {
       printer.printGenres();
    }


}
