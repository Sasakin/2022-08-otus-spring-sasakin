package ru.otus.spring.book.page.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.AuthorService;
import ru.otus.spring.book.services.BookService;
import ru.otus.spring.book.services.GenreService;

@Controller
@AllArgsConstructor
public class PageController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        return "edit";
    }

    /*@PostMapping("/edit")
    public String savebook(Book book) {
        bookService.save(book);
        return "redirect:/";
    }*/

    @GetMapping("/book/add")
    public String beforeBookAdd(Model model) {
        Book book = new Book();
        book.setAuthor(new Author(""));
        book.setGenre(new Genre(""));
        model.addAttribute("book", book);
        return "addBook";
    }

    /*@PostMapping("/book/save")
    public String saveBook(Book book,  RedirectAttributes ra) {
        bookService.save(book);
        ra.addFlashAttribute("message", "The book has been saved successfully.");
        return "redirect:/";
    }*/

    /*@GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }*/
}
