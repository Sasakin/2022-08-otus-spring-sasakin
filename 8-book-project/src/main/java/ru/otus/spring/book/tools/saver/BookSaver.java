package ru.otus.spring.book.tools.saver;

public interface BookSaver {

    BookSaver authorName(String authorName);

    BookSaver genreTitle(String genreTitle);

    BookSaver bookTitle(String bookTitle);

    BookSaver bookComments(String... comments);

    void save();
}
