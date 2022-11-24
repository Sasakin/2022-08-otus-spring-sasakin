package ru.otus.spring.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.services.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @RequestMapping(value = "/authors", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Author> authors(@RequestParam(value = "searchstr", required = false) String searchstr) {
        System.out.println("searchstr: " + searchstr);
        List<Author> authors = service.getAll();
        if (StringUtils.isEmpty(searchstr)) {
            return authors.stream()
                    .limit(15)
                    .collect(Collectors.toList());
        }

        return authors.stream()
                .filter(author -> author.getName()
                        .toLowerCase()
                        .contains(searchstr))
                .limit(15)
                .collect(Collectors.toList());
    }
}
