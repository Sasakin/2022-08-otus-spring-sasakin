package ru.otus.spring.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {

    @Transient
    public static final String SEQUENCE_NAME = "author_sequence";

    @Id
    private Long id;

    private String name;

    @DocumentReference(lazy = true, lookup = "{ 'authors' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Book> books;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
