package ru.otus.spring.exam.tools;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ExamScanner {
    private Scanner scanner;

    public ExamScanner() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() {
        return scanner.next();
    }

    public int getNextInt() {
        return scanner.nextInt();
    }
}
