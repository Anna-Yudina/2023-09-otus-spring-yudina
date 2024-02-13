package ru.otus.hw.actuators;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class PropertiesValueHealthCheck implements HealthIndicator {

    @Value("${health.value}")
    private Integer number;

    @Override
    public Health health() {
        if (number % 2 == 0) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message","Опаньки!")
                    .build();
        }
        return Health.up().build();
    }
}
