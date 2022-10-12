package ru.otus.spring.exam.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Data
public class ExamProperty {
    @Value("${exam.test.src}")
    private String examTestSrc;

    @Value("${exam.test.count.answers.pass}")
    private Integer countAnswersForPass;

}
