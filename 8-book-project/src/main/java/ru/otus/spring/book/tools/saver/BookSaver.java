package ru.otus.spring.book.tools.saver;

public interface BookSaver {

    void save(String authorName, String genreTitle, String bookTitle, String... comments);
}
