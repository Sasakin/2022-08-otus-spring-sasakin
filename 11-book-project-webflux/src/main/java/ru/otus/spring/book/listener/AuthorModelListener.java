package ru.otus.spring.book.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.services.SequenceGeneratorService;

@Component
public class AuthorModelListener extends AbstractMongoEventListener<Author> {

    private SequenceGeneratorService sequenceGeneratorService;

    public AuthorModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Author> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME).block());
        }
    }
}
