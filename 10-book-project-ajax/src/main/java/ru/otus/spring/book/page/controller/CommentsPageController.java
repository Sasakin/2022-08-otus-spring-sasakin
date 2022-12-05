package ru.otus.spring.book.page.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.webjars.NotFoundException;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Comment;
import ru.otus.spring.book.services.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentsPageController {

    private final BookService bookService;

    @GetMapping("/comment/list")
    public String commentList(@RequestParam("id") long id, Model model) {
        /*Book book = bookService.getById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        List<Comment> comments = book.getComments();
        model.addAttribute("comments", comments);*/
        return "comments";
    }


    @GetMapping("/comment/add")
    public String addCommentGet(@RequestParam("id") long id, Model model) {
        /*Book book = bookService.getById(id).orElseThrow(NotFoundException::new);
        Comment comment = new Comment();
        comment.setBook(book);
        model.addAttribute("comment", comment);*/
        return "addAuthor";
    }

    @PostMapping("/comment/add")
    public String addCommentPost(Comment comment) {
        Book book = comment.getBook();
        book.getComments().add(comment);
        bookService.save(book);
        return "redirect:/comment/list?id=" + book.getId();
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
