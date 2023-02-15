package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.hystrix.services.HystrixCommentService;
import ru.otus.spring.book.rest.controller.dto.CommentDto;
import ru.otus.spring.book.rest.controller.wrapper.CommentWrapper;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentsController {

    private final HystrixCommentService service;

    @GetMapping("/list")
    public List<CommentDto> commentList(@RequestParam("id") long id) {
        return service.commentList(id);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveComment(@RequestBody CommentWrapper commentWrapper) {
        return service.saveComment(commentWrapper);
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("bookId") long bookId, long commentId) {
        service.deleteComment(bookId, commentId);
        return "redirect:/comment/list?id=" + bookId;
    }

}
