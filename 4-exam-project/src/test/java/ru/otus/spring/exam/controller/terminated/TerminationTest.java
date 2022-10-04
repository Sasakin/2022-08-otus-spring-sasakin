package ru.otus.spring.exam.controller.terminated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.exam.controller.ControllerStatus;
import ru.otus.spring.exam.controller.Result;
import ru.otus.spring.exam.controller.termination.ExamResultViewer;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
        })
public class TerminationTest {

    @MockBean
    private Result result;

    @Autowired
    private ExamResultViewer termination;

    @Test
    void testTermination() {
        Mockito.when(result.getCountRightAnswersInt()).thenReturn(4);
        Mockito.when(result.getQuestionsCountInt()).thenReturn(10);

        Mockito.when(result.getName()).thenReturn("Maxim");
        Mockito.when(result.getSurname()).thenReturn("Mister");

        Assertions.assertEquals(termination.showResult(), ControllerStatus.TERMINATED);
    }
}
