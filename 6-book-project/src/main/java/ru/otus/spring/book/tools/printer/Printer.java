package ru.otus.spring.book.tools.printer;

public interface Printer {


    void printBooks();

    void printAuthors();

    void printGenres();

    void printCommentsByBookId(Long bookId);
}
