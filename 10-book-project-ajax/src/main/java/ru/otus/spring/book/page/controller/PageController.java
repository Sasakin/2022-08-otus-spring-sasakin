package ru.otus.spring.book.page.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

@Controller
@AllArgsConstructor
public class PageController {

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return "edit";
    }

    @PostMapping("/book/edit")
    public String savebook(RedirectAttributes ra) {
        ra.addFlashAttribute("message", "The book has been edited successfully.");
        return "redirect:/";
    }

    @GetMapping("/book/add")
    public String beforeBookAdd(Model model) {
        Book book = new Book();
        book.setAuthor(new Author(""));
        book.setGenre(new Genre(""));
        model.addAttribute("book", book);
        return "addBook";
    }

    @PostMapping("/book/save")
    public String saveBook(RedirectAttributes ra) {
        ra.addFlashAttribute("message", "The book has been saved successfully.");
        return "redirect:/";
    }

    @GetMapping("/book/delete")
    public String deleteBook() {
        return "redirect:/";
    }
}
