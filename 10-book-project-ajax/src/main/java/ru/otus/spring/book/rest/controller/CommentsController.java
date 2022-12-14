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
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.rest.controller.dto.CommentDto;
import ru.otus.spring.book.rest.controller.wrapper.CommentWrapper;
import ru.otus.spring.book.services.BookService;
import ru.otus.spring.book.services.CommentsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentsController {

    private final BookService bookService;

    private final CommentsService commentsService;

    @GetMapping("/list")
    public List<CommentDto> commentList(@RequestParam("id") long id) {
        Optional<Book> bookOpt = bookService.getById(id);

        if(bookOpt.isPresent()) {
            List<Comment> comments = commentsService.getCommentsByBook(bookOpt.get());
            return comments.stream()
                    .map(comment -> CommentDto.toDto(comment))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveComment(@RequestBody CommentWrapper commentWrapper) {
        Optional<Book> bookOpt = bookService.getById(commentWrapper.getBookId());
        if(bookOpt.isPresent()) {
            Book book = bookOpt.get();
            Comment comment = new Comment(commentWrapper.getText(), book);
            book.getComments().add(comment);
            bookService.save(book);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/comment/delete")
    public String delleteComment(@RequestParam("bookId") long bookId, long commentId) {
        Book book = bookService.getById(bookId).orElse(null);
        if(book != null) {
            Comment comment = book.getComments().stream()
                    .filter(cmt -> cmt.getId().equals(commentId))
                    .findFirst().get();
            book.getComments().remove(comment);
            bookService.save(book);
        }
        return "redirect:/comment/list?id=" + bookId;
    }

}
