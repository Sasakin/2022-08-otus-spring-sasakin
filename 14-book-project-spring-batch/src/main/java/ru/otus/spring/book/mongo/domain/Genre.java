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
@Document(collection = "genres")
public class Genre {

    @Transient
    public static final String SEQUENCE_NAME = "genre_sequence";

    @Id
    private ObjectId id;

    private String title;

   /* public Genre(Long id, String title) {
        this.id = id;
        this.title = title;
    }*/
}
