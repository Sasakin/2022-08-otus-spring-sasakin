package ru.otus.spring.controller.termination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.controller.ExamResult;
import ru.otus.spring.property.ExamProperty;

@Component
public class ExamTerminationImpl implements ExamTermination {

    private ExamResult result;

    private ExamProperty property;

    public ExamTerminationImpl(@Autowired ExamResult result, @Autowired ExamProperty property) {
        this.result = result;
        this.property = property;
    }

    @Override
    public void terminateExam() {
        int countRightAnswers = result.getCountRightAnswersInt();
        System.out.printf("%s %s your result: %d / %d\n",
                result.getName(), result.getSurname(),
                countRightAnswers, result.getQuestionsCountInt());

        if(countRightAnswers >= property.getCountAnswersForPass()) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }
}
