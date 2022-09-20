package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.controller.TestController;

public class Main {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TestController controller = context.getBean(TestController.class);
        controller.prepareTest();
        controller.startTesting();
        controller.finishTesting();
    }
}
