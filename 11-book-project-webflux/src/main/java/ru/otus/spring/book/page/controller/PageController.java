package ru.otus.spring.book.page.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.BookService;

@Controller
@AllArgsConstructor
public class PageController {

    private BookService bookService;

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return "edit";
    }

    @GetMapping("/book/add")
    public String beforeBookAdd(Model model) {
        Book book = new Book();
        book.setAuthor(new Author(0l, ""));
        book.setGenre(new Genre(0l,""));
        model.addAttribute("book", book);
        return "addBook";
    }

    @GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
