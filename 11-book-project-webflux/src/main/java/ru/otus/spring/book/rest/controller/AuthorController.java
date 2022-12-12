package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorDao authorService;

    @GetMapping({"/api/author/list"})
    public List<AuthorDto> getAuthors() {
        List<Author> authors = new ArrayList<>(); // authorService.getAll();
        return authors.stream().map(author -> AuthorDto.toDto(author)).collect(Collectors.toList());
    }
}
