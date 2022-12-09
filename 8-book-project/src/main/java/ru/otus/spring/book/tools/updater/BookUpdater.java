package ru.otus.spring.book.tools.updater;

public interface BookUpdater {

    void update(Long bookId, String... comments);

}
