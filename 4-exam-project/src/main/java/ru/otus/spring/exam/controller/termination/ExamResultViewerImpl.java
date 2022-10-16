package ru.otus.spring.exam.controller.termination;

import org.springframework.stereotype.Component;
import ru.otus.spring.exam.controller.ControllerStatus;
import ru.otus.spring.exam.controller.Result;
import ru.otus.spring.exam.exception.TestNotPassedException;
import ru.otus.spring.exam.localisation.MessageSourceHolder;
import ru.otus.spring.exam.property.ExamProperty;

@Component
public class ExamResultViewerImpl implements ExamResultViewer {

    private MessageSourceHolder msgHolder;
    private Result result;
    private ExamProperty property;

    public ExamResultViewerImpl(Result result,
                                ExamProperty property,
                                MessageSourceHolder msgHolder) {
        this.result = result;
        this.property = property;
        this.msgHolder = msgHolder;
    }

    @Override
    public ControllerStatus showResult() throws TestNotPassedException {

        if((result.getQuestionsCountInt() == 0) && (result.getName() == null)) {
            throw new TestNotPassedException();
        }

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
