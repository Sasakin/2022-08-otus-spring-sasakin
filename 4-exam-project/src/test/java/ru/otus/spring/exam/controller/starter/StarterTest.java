package ru.otus.spring.exam.controller.starter;

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
import ru.otus.spring.exam.tools.ExamScanner;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class StarterTest {
    @MockBean
    private ExamScanner scanner;

    @Autowired
    private ExamStarter starter;

    @MockBean
    private Result result;

    @Test
    void testStarter() throws Throwable {
        Mockito.when(scanner.getNextInt()).thenReturn(2);
        Mockito.when(result.getName()).thenReturn("Maxim");
        Mockito.when(result.getSurname()).thenReturn("S");
        Mockito.when(result.getQuestionsCount()).thenReturn(new AtomicInteger());
        Mockito.when(result.getCntRightAnswers()).thenReturn(new AtomicInteger());

        Assertions.assertEquals(starter.start(), ControllerStatus.STARTED);
    }
}
