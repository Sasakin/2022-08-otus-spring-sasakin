package ru.otus.spring.book.hystrix.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.rest.controller.dto.CommentDto;
import ru.otus.spring.book.rest.controller.wrapper.CommentWrapper;
import ru.otus.spring.book.services.BookService;
import ru.otus.spring.book.services.CommentsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HystrixCommentServiceImpl implements HystrixCommentService {
    private final BookService bookService;

    private final CommentsService commentsService;


    @Override
    @HystrixCommand(commandKey="commentList", fallbackMethod="buildFallbackCommentList")
    public List<CommentDto> commentList(long id) {
        Optional<Book> bookOpt = bookService.getById(id);

        if(bookOpt.isPresent()) {
            List<Comment> comments = commentsService.getCommentsByBook(bookOpt.get());
            return comments.stream()
                    .map(comment -> CommentDto.toDto(comment))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @HystrixCommand(commandKey="saveComment", fallbackMethod="buildFallbackResponseEntityVoid")
    public ResponseEntity<Void> saveComment(CommentWrapper commentWrapper) {
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

    @Override
    @HystrixCommand(commandKey="saveComment", fallbackMethod="doFallbackDelete")
    public void deleteComment(long bookId, long commentId) {
        Book book = bookService.getById(bookId).orElse(null);
        if(book != null) {
            Comment comment = book.getComments().stream()
                    .filter(cmt -> cmt.getId().equals(commentId))
                    .findFirst().get();
            book.getComments().remove(comment);
            bookService.save(book);
        }
    }

    private void doFallbackDelete(long bookId, long commentId) {

    }


    public ResponseEntity<Void> buildFallbackResponseEntityVoid(CommentWrapper commentWrapper) {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public List<CommentDto> buildFallbackCommentList(long id) {
        CommentDto dto = new CommentDto(0l, "N/A");
        List<CommentDto> commentsList = new ArrayList<>();
        commentsList.add(dto);
        return commentsList;
    }
}
