package ru.otus.spring.book.tools.printer;

public interface Printer {


    void printBooks();

    void printCommentsByBookId(Long bookId);
}
