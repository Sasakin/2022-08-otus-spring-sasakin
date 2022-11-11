package ru.otus.spring.book.tools.updater;

import ru.otus.spring.book.domain.Book;

import java.util.Optional;

public interface BookUpdater {

    BookUpdater byId(Long bookId);

    BookUpdater addBookComments(String... comments);

    void update();

}
