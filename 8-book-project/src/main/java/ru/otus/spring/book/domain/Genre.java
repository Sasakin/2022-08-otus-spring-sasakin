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
@Document(collection = "genres")
public class Genre {

    @Transient
    public static final String SEQUENCE_NAME = "genres_sequence";

    @Id
    private Long id;

    private String title;

    public Genre(String title) {
        this.title = title;
    }
}
