package ru.otus.spring.exam.controller.registration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.exam.controller.ControllerStatus;
import ru.otus.spring.exam.tools.ExamScanner;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class PreparationTest {

    @MockBean
    private ExamScanner scanner;

    @Autowired
    private ExamRegistration preparation;

    @Test
    void testPreparation() {
        Mockito.when(scanner.getNextInt()).thenReturn(2);
        Mockito.when(scanner.getInput()).thenReturn("Maxim");

        Assertions.assertEquals(preparation.registerUser(), ControllerStatus.PREPARATION);
    }
}
