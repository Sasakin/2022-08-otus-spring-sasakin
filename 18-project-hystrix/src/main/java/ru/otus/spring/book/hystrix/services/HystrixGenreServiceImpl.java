package ru.otus.spring.book.hystrix.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.rest.controller.dto.GenreDto;
import ru.otus.spring.book.services.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HystrixGenreServiceImpl implements HystrixGenreService {

    private final GenreService genreService;

    @Override
    @HystrixCommand(commandKey="getGenres", fallbackMethod="buildFallbackGenreList")
    public List<GenreDto> getGenres() {
        List<Genre> genres = genreService.getAll();
        return genres.stream().map(genre -> GenreDto.toDto(genre)).collect(Collectors.toList());
    }

    public List<GenreDto> buildFallbackGenreList() {
        GenreDto genreDto = new GenreDto(0L,"N/A");

        List<GenreDto> genreList = new ArrayList<>();
        genreList.add(genreDto);
        return genreList;
    }
}
