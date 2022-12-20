package ru.otus.spring.book.rest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.book.domain.Comment;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {

    private String text;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getText());
    }

    public static Comment toComment(CommentDto commentDto) {
        return new Comment(commentDto.getText());
    }
}
