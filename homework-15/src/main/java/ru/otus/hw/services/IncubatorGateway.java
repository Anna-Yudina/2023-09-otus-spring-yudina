package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.models.Chicken;
import ru.otus.hw.models.Egg;
import java.util.List;

@MessagingGateway
public interface IncubatorGateway {

    @Gateway(requestChannel = "eggChannel", replyChannel = "chickenChannel")
    List<Chicken> process(List<Egg> eggs);
}
