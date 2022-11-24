package ru.otus.spring.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.AuthorService;
import ru.otus.spring.book.services.BookService;
import ru.otus.spring.book.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.getById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);

        return "edit";
    }

    @PostMapping("/edit")
    public String savebook(Book book) {
        bookService.save(book);
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
    public String saveBook(Book book,  RedirectAttributes ra) {
        bookService.save(book);
        ra.addFlashAttribute("message", "The book has been saved successfully.");
        return "redirect:/";
    }

    @GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
