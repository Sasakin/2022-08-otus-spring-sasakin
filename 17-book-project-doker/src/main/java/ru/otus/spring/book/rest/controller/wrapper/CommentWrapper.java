package ru.otus.spring.book.rest.controller.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentWrapper {
    private long bookId;

    private String text;
}
