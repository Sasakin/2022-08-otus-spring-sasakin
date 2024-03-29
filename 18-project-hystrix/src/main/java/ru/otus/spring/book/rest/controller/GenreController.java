package ru.otus.spring.book.rest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.hystrix.services.HystrixGenreService;
import ru.otus.spring.book.rest.controller.dto.GenreDto;
import ru.otus.spring.book.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
public class GenreController {

    private final HystrixGenreService service;

    @GetMapping({"/api/genre/list"})
    public List<GenreDto> getGenres() {
        return service.getGenres();
    }
}
