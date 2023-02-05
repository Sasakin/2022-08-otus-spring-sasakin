package ru.otus.spring.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCircuitBreaker
public class Main {

    public static void main(String[] args) throws Throwable {
        ApplicationContext context = SpringApplication.run(Main.class);

        System.out.printf("App link: %n%s%n",
                "http://localhost:8080");
    }
}
