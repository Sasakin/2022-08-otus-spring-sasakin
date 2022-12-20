package ru.otus.spring.book.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.book.domain.Genre;
import ru.otus.spring.book.services.SequenceGeneratorService;

@Component
public class GenreModelListener extends AbstractMongoEventListener<Genre> {

    private SequenceGeneratorService sequenceGeneratorService;

    public GenreModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Genre> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Genre.SEQUENCE_NAME).block());
        }
    }
}
