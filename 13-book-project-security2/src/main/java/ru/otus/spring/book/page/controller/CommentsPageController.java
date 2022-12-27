package ru.otus.spring.book.page.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.book.services.SecurityServices;

@Controller
@RequiredArgsConstructor
public class CommentsPageController {

    private final SecurityServices securityServices;

    @GetMapping("/comment/list")
    public String commentList(@RequestParam("id") long id, Model model) {
        securityServices.onlyUser();
        model.addAttribute("id", id);
        return "comments";
    }


    @GetMapping("/comment/add")
    public String addCommentGet(@RequestParam("bookId") long id, Model model) {
        securityServices.onlyUser();
        model.addAttribute("bookId", id);
        return "addComment";
    }

}
