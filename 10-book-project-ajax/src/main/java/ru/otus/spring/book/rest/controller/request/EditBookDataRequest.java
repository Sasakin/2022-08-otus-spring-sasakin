package ru.otus.spring.book.rest.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.book.rest.controller.dto.BookDto;

@Data
@AllArgsConstructor
public class EditBookDataRequest {
    private BookDto book;
}
