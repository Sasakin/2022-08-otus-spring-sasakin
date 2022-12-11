package ru.otus.spring.book.rest.controller.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.book.domain.Comment;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private Long id;

    private String text;

    public static CommentDto toDto(@NotNull Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }

    public static Comment toComment(CommentDto commentDto) {
        return new Comment(commentDto.getId(), commentDto.getText());
    }
}
