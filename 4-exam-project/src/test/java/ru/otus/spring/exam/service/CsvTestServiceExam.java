package ru.otus.spring.exam.service;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.exam.domain.Test;

import java.util.List;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class CsvTestServiceExam {
    @Autowired
    private CsvExamService service;

    @org.junit.jupiter.api.Test
    public void readCsv() throws Throwable{
        //InputStream testStream = Main.class.getClassLoader().getResourceAsStream("ru/otus/spring/exam/test.csv");

        //CsvExamService service = new CsvExamServiceImpl(new StringArrTestConverterImpl(), new CSVReader(new InputStreamReader(testStream)));
        List<Test> tests = service.readTests();

        Assertions.assertEquals(tests.get(0).getQuestion(), "What can Java be used for?");
        Assertions.assertEquals(tests.get(0).getOptions().size(), 4);
    }

}
