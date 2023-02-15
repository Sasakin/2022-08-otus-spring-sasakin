package ru.otus.spring.book.hystrix.services;

import org.springframework.http.ResponseEntity;
import ru.otus.spring.book.rest.controller.dto.CommentDto;
import ru.otus.spring.book.rest.controller.wrapper.CommentWrapper;

import java.util.List;

public interface HystrixCommentService {

    List<CommentDto> commentList(long id);

    ResponseEntity<Void> saveComment(CommentWrapper commentWrapper);

    void deleteComment(long bookId, long commentId);
}
