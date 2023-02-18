package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.hystrix.services.HystrixAuthorService;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.services.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
public class AuthorController {

    private final HystrixAuthorService service;

    @GetMapping({"/api/author/list"})
    public List<AuthorDto> getAuthors() {
        return service.getAuthors();
    }
}
