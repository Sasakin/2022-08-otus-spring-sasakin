package ru.otus.spring.book;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.dao.BookDao;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

import java.util.List;

@ShellComponent
public class BookShellController {

    private BookDao bookDao;

    private AuthorDao authorDao;

    private GenreDao genreDao;

    public BookShellController(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @ShellMethod(key = "add-book", value = "Add book to library")
    public void addBook(@ShellOption("-author")String authorName, @ShellOption("-genre") String genreTitle, @ShellOption("-book") String bookTitle) {
        Author author = new Author(Long.valueOf(authorDao.count()), authorName);
            authorDao.insert(author);

        Genre genre = new Genre(Long.valueOf(genreDao.count()), genreTitle);
            genreDao.insert(genre);

        Book book = new Book(Long.valueOf(bookDao.count()), bookTitle, author, genre);

        bookDao.insert(book);

    }

    @ShellMethod(key = "print-books", value = "Print all books")
    public void printBooks() {
        List<Book> bookList = bookDao.getAll();

        bookList.forEach(System.out :: println);
    }

    @ShellMethod(key = "print-authors", value = "Print all authors")
    public void printAuthors() {
        List<Author> authors = authorDao.getAll();

        authors.forEach(System.out :: println);
    }

    @ShellMethod(key = "print-genres", value = "Print all genres")
    public void printGenres() {
        List<Genre> genres = genreDao.getAll();

        genres.forEach(System.out :: println);
    }


}
