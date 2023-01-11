package ru.otus.spring.book.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.spring.book.jpa.domain.Genre;
import ru.otus.spring.book.mongo.services.SequenceGeneratorService;

@AllArgsConstructor
@Component
public class GenreItemProcessor implements ItemProcessor<Genre, ru.otus.spring.book.mongo.domain.Genre> {

    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public ru.otus.spring.book.mongo.domain.Genre process(Genre genre) throws Exception {
        ru.otus.spring.book.mongo.domain.Genre resGenre = new ru.otus.spring.book.mongo.domain.Genre();
        resGenre.setId(sequenceGeneratorService.generateSequence());
        resGenre.setTitle(genre.getTitle());
        return resGenre;
    }
}