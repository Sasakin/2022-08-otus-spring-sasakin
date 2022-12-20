package ru.otus.spring.book.page.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.GenreService;
import ru.otus.spring.book.services.SecurityServices;

@Controller
@RequiredArgsConstructor
public class GenrePageController {

    private final SecurityServices securityServices;

    @GetMapping("/genre/add")
    public String addGenreGet(Model model) {
        securityServices.onlyUser();
        model.addAttribute("genre", new Genre());
        return "addAuthor";
    }

    @PostMapping("/genre/add")
    public String addGenrePost(Genre genre) {
        securityServices.onlyUser();
        return "redirect:/";
    }
}
