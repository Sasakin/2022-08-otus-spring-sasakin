package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.GenreService;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService service;

    @GetMapping("/genre/add")
    public String addGenreGet(Model model) {
        model.addAttribute("genre", new Genre());
        return "addGenre";
    }

    @PostMapping("/genre/add")
    public String addGenrePost(Genre genre) {
        service.insert(genre);
        return "redirect:/";
    }
}
