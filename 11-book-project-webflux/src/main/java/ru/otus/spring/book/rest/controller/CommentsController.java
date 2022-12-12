package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.dao.BookDao;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.rest.controller.dto.CommentDto;
import ru.otus.spring.book.rest.controller.wrapper.CommentWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentsController {

    private final BookDao bookService;

    @GetMapping("/list")
    public List<CommentDto> commentList(@RequestParam("id") long id) {
        Optional<Book> bookOpt = Optional.empty();// bookService.getById(id);

        if(bookOpt.isPresent()) {
            List<Comment> comments = new ArrayList<>();// commentsService.getCommentsByBook(bookOpt.get());
            return comments.stream()
                    .map(comment -> CommentDto.toDto(comment))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveComment(@RequestBody CommentWrapper commentWrapper) {
        Optional<Book> bookOpt = Optional.empty();// bookService.getById(commentWrapper.getBookId());
        if(bookOpt.isPresent()) {
            Book book = bookOpt.get();
            Comment comment = new Comment(commentWrapper.getText());
            book.getComments().add(comment);
            //bookService.save(book);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("bookId") long bookId, String commentText) {
        Book book = new Book();// bookService.getById(bookId).orElse(null);
        if(book != null) {
            Comment comment = book.getComments().stream()
                    .filter(cmt -> cmt.getText().equals(commentText))
                    .findFirst().get();
            book.getComments().remove(comment);
            //bookService.save(book);
        }
        return "redirect:/comment/list?id=" + bookId;
    }

}
