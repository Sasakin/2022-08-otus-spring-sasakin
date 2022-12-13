package ru.otus.spring.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Transient
    public static final String SEQUENCE_NAME = "books_sequence";

    public Book(Long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Id
    private Long id;

    private String title;

    @BsonProperty("author")
    private Long authorId;

    @BsonProperty("genre")
    private Long genreId;

    @Transient
    private Author author;

    @Transient
    private Genre genre;

    private List<Comment> comments = new ArrayList<>();

    public Book(Long id, String title, Author author, Genre genre, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }
}
