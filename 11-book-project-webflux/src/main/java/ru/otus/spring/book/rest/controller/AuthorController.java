package ru.otus.spring.book.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.book.dao.AuthorDao;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;

@RestController()
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorDao authorDao;

    @GetMapping({"/api/author/list"})
    public Flux<AuthorDto> getAuthors() {
        return authorDao.findAll().map(author -> AuthorDto.toDto(author));
    }
}
