package ru.otus.spring.book.page.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.book.domain.Author;

@Controller
@RequiredArgsConstructor
public class AuthorPageController {

    @GetMapping("/author/add")
    public String addAuthorGet(Model model) {
        model.addAttribute("author", new Author());
        return "addAuthor";
    }

    @PostMapping("/author/add")
    public String addAuthorPost(Author author) {
        return "redirect:/";
    }
}
