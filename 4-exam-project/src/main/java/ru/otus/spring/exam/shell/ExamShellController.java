package ru.otus.spring.exam.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.exam.controller.registration.ExamRegistration;
import ru.otus.spring.exam.controller.starter.ExamStarter;
import ru.otus.spring.exam.controller.termination.ExamResultViewer;
import ru.otus.spring.exam.exception.NoRegisterUserException;
import ru.otus.spring.exam.localisation.MessageSourceHolder;

@ShellComponent
public class ExamShellController {

    private MessageSourceHolder msgHolder;

    private final ExamRegistration preparation;

    private final ExamStarter starter;

    private final ExamResultViewer termination;

    public ExamShellController(MessageSourceHolder msgHolder,
                               ExamRegistration preparation,
                               ExamStarter starter,
                               ExamResultViewer termination) {
        this.msgHolder = msgHolder;
        this.preparation = preparation;
        this.starter = starter;
        this.termination = termination;
    }

    @ShellMethod(key = "register-user", value = "Register user before testing")
    public void registration() {
        preparation.registerUser();
    }

    @ShellMethod(key = "start-test", value = "Start testing")
    public String startTest() throws Throwable {
        try {
            starter.start();
            String testFinishedMsg = msgHolder.getMessage("test.finished", "Test finished");
            return testFinishedMsg;
        } catch (NoRegisterUserException e) {
            String exceptionMsg = msgHolder.getMessage("exception.registration", "User not registered");
            return exceptionMsg;
        }
    }

    @ShellMethod(key = "test-result", value = "Show test result")
    public String testResult() throws Throwable {
        try {
            termination.showResult();
            return "";
        } catch (NoRegisterUserException e) {
            String exceptionMsg = msgHolder.getMessage("exception.testing", "Test not passed");
            return exceptionMsg;
        }
    }

}
