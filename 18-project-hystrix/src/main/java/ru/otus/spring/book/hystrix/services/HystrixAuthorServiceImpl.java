package ru.otus.spring.book.hystrix.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.rest.controller.dto.AuthorDto;
import ru.otus.spring.book.services.AuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HystrixAuthorServiceImpl implements HystrixAuthorService {

    private final AuthorService authorService;

    @Override
    @HystrixCommand(commandKey="getAuthors", fallbackMethod="buildFallbackAuthorList")
    public List<AuthorDto> getAuthors() {
        List<Author> authors = authorService.getAll();
        return authors.stream().map(author -> AuthorDto.toDto(author)).collect(Collectors.toList());
    }

    public List<AuthorDto> buildFallbackAuthorList() {
        AuthorDto authorDto = new AuthorDto(0L, "N/A");

        List<AuthorDto> authorList = new ArrayList<>();
        authorList.add(authorDto);
        return authorList;
    }
}
