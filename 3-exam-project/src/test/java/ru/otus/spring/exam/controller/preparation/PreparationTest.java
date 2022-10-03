package ru.otus.spring.exam.controller.preparation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.exam.controller.ControllerStatus;
import ru.otus.spring.exam.tools.ExamScanner;

@SpringBootTest
public class PreparationTest {

    @MockBean
    private ExamScanner scanner;

    @Autowired
    private ExamPreparation preparation;

    @Test
    void testPreparation() {
        Mockito.when(scanner.getNextInt()).thenReturn(2);
        Mockito.when(scanner.getInput()).thenReturn("Maxim");

        Assertions.assertEquals(preparation.prepareExam(), ControllerStatus.PREPARATION);
    }
}
