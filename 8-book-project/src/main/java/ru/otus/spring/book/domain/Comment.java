package ru.otus.spring.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "book_comments")
public class Comment {

    @Transient
    public static final String SEQUENCE_NAME = "comments_sequence";
    @Id
    private Long id;

    private String text;

/*    *//*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")*//*
    private Book book;*/


    public Comment(String text, Book book) {
        this.text = text;
        //this.book = book;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '}';
    }
}
