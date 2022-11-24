package ru.otus.spring.book.tools.updater;

public interface BookUpdater {

    BookUpdater byId(Long bookId);

    BookUpdater addBookComments(String... comments);

    void update();

}
