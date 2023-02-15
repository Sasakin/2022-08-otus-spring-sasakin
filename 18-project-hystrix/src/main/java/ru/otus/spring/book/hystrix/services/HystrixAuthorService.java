package ru.otus.spring.book.hystrix.services;

import ru.otus.spring.book.rest.controller.dto.AuthorDto;

import java.util.List;

public interface HystrixAuthorService {
    List<AuthorDto> getAuthors();
}
