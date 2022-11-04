package ru.otus.spring.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
/*@Getter
@Setter*/
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Id
    private Long id;

    private String title;

    private Author author;

    private Genre genre;

    //@Fetch(FetchMode.SUBSELECT)
    //@OneToMany(targetEntity = Comment.class, mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

}
