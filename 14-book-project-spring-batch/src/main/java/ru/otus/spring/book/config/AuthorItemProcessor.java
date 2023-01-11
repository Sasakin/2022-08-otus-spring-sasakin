package ru.otus.spring.book.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.spring.book.jpa.domain.Author;
import ru.otus.spring.book.mongo.services.SequenceGeneratorService;

@AllArgsConstructor
@Component
public class AuthorItemProcessor implements ItemProcessor<Author, ru.otus.spring.book.mongo.domain.Author> {

    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public ru.otus.spring.book.mongo.domain.Author process(Author author) throws Exception {
        ru.otus.spring.book.mongo.domain.Author resAuthor = new ru.otus.spring.book.mongo.domain.Author();
        resAuthor.setId(sequenceGeneratorService.generateSequence());
        resAuthor.setName(author.getName());
        return resAuthor;
    }
}
