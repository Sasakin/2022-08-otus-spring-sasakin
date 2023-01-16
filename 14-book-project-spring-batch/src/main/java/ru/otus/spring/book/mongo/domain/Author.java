package ru.otus.spring.book.mongo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {

    @Transient
    public static final String SEQUENCE_NAME = "author_sequence";

    @Id
    private ObjectId id;

    private String name;

    /*public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }*/
}
