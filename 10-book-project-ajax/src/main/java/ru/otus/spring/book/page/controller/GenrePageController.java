package ru.otus.spring.book.page.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.GenreService;

@Controller
@RequiredArgsConstructor
public class GenrePageController {

    @GetMapping("/genre/add")
    public String addGenreGet(Model model) {
        model.addAttribute("genre", new Genre());
        return "addAuthor";
    }

    @PostMapping("/genre/add")
    public String addGenrePost(Genre genre) {
        return "redirect:/";
    }
}
