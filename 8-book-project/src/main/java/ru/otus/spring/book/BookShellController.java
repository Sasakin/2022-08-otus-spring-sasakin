package ru.otus.spring.book;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.book.tools.printer.Printer;
import ru.otus.spring.book.tools.saver.BookSaver;
import ru.otus.spring.book.tools.updater.BookUpdater;

@ShellComponent
@AllArgsConstructor
public class BookShellController {

    private BookSaver bookSaver;

    private BookUpdater bookUpdater;

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

    @ShellMethod(key = "add-comment-to-book", value = "Add comment to book")
    public void addCommentToBook(@ShellOption("-id") Long bookId,
                                 @ShellOption("-comment") String comment) {

        bookUpdater.byId(bookId).addBookComments(comment).update();
    }

    @ShellMethod(key = "print-comments-by-bookId", value = "Print all comments by book")
    public void printCommentsByBookId(@ShellOption("-id") Long bookId) {
        printer.printCommentsByBookId(bookId);
    }


}
