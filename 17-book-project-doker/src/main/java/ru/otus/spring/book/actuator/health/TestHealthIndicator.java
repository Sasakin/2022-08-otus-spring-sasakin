package ru.otus.spring.book.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TestHealthIndicator implements HealthIndicator {

    private final Random random = new Random();

    @Override
    public Health health() {
        boolean testServerIsDown = random.nextBoolean();
        if(testServerIsDown) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Not work!")
                    .build();
        } else {
            return Health.up()
                    .withDetail("message", "work")
                    .build();
        }
    }

}
