package ru.otus.hw.services;

import ru.otus.hw.models.Chick;
import ru.otus.hw.models.Chicken;
import ru.otus.hw.models.Egg;

public interface IncubatorService {
    Chick incubationEgg(Egg egg);

    Chicken incubationChick(Chick chick);
}
