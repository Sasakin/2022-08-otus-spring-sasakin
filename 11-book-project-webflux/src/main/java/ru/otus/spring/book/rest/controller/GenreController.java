package ru.otus.spring.book.rest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.book.dao.GenreDao;
import ru.otus.spring.book.rest.controller.dto.GenreDto;

@RestController()
@RequiredArgsConstructor
public class GenreController {

    private final GenreDao genreDao;

    @GetMapping({"/api/genre/list"})
    public Flux<GenreDto> getGenres() {
        return genreDao.findAll().map(genre -> GenreDto.toDto(genre));
    }
}
