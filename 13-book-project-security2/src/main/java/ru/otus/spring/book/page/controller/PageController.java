package ru.otus.spring.book.page.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.SecurityServices;

@Controller
@AllArgsConstructor
public class PageController {

    private SecurityServices securityServices;

    @GetMapping("/")
    public String listPage() {
        return "index";
    }

    @GetMapping("/public")
    public String authenticatedPage() {
        return "public";
    }

    @GetMapping("/list")
    public String indexPage() {
        securityServices.onlyUser();
        return "list";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        securityServices.onlyUser();
        model.addAttribute("id", id);
        return "edit";
    }

    @GetMapping("/book/add")
    public String beforeBookAdd(Model model) {
        securityServices.onlyUser();
        Book book = new Book();
        book.setAuthor(new Author(""));
        book.setGenre(new Genre(""));
        model.addAttribute("book", book);
        return "addBook";
    }

    @GetMapping("/book/delete")
    public String deleteBook() {
        securityServices.onlyUser();
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/admin")
    public String admin() {
        securityServices.onlyAdmin();
        return "/admin";
    }

}
