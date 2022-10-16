package ru.otus.spring.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.exam.property.LocaleProperty;

@SpringBootApplication
@EnableConfigurationProperties(LocaleProperty.class)
public class Main {

    public static void main(String[] args) throws  Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
       /* Application application = context.getBean(Application.class);
        application.run();*/
    }
}
