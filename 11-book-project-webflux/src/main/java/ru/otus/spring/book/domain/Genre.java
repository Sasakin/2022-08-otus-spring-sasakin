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
@Document(collection = "genres")
public class Genre {

    @Transient
    public static final String SEQUENCE_NAME = "genre_sequence";

    @Id
    private Long id;

    private String title;

    @DocumentReference(lazy = true, lookup = "{ 'genres' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Book> books;

    public Genre(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
