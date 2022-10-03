package ru.otus.spring.exam.controller.termination;

import org.springframework.stereotype.Component;
import ru.otus.spring.exam.controller.ControllerStatus;
import ru.otus.spring.exam.controller.ExamResult;
import ru.otus.spring.exam.controller.Result;
import ru.otus.spring.exam.localisation.MessageSourceHolder;
import ru.otus.spring.exam.property.ExamProperty;

@Component
public class ExamTerminationImpl implements ExamTermination {

    private MessageSourceHolder msgHolder;
    private Result result;
    private ExamProperty property;

    public ExamTerminationImpl(Result result,
                               ExamProperty property,
                               MessageSourceHolder msgHolder) {
        this.result = result;
        this.property = property;
        this.msgHolder = msgHolder;
    }

    @Override
    public ControllerStatus terminateExam() {
        int countRightAnswers = result.getCountRightAnswersInt();
        String messageResult = msgHolder.getMessage("message.result", "%s %s your result: %d / %d \n");

        System.out.printf(messageResult,
                result.getName(), result.getSurname(),
                countRightAnswers, result.getQuestionsCountInt());

        if(countRightAnswers >= property.getCountAnswersForPass()) {
            String testPassMsg = msgHolder.getMessage("exam.pass", "Test passed");
            System.out.println(testPassMsg);
        } else {
            String testFailMsg = msgHolder.getMessage("exam.fail", "Test failed");
            System.out.println(testFailMsg);
        }
        return ControllerStatus.TERMINATED;
    }
}
