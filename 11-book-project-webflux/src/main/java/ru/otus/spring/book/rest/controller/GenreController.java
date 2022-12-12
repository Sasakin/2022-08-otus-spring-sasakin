package ru.otus.spring.book.rest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.rest.controller.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
public class GenreController {

    private final GenreDao genreService;

    @GetMapping({"/api/genre/list"})
    public List<GenreDto> getGenres() {
        List<Genre> genres = new ArrayList<>(); //genreService.getAll();
        return genres.stream().map(genre -> GenreDto.toDto(genre)).collect(Collectors.toList());
    }
}
