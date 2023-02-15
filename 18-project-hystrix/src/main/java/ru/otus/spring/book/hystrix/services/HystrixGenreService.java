package ru.otus.spring.book.hystrix.services;

import ru.otus.spring.book.rest.controller.dto.GenreDto;

import java.util.List;

public interface HystrixGenreService {

    List<GenreDto> getGenres();
}
