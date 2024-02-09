package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Chick;
import ru.otus.hw.models.Chicken;
import ru.otus.hw.models.Egg;
import ru.otus.hw.util.Util;

@Slf4j
@Service
public class IncubatorServiceImpl implements IncubatorService {

    @Override
    public Chick incubationEgg(Egg egg) {
        log.info("{} incubation process ... ", egg.toString());

        Util.delay(2000);
        Chick chick = new Chick(egg.getId(), egg.getName());
        log.info("Transformation into {}", chick.toString());
        return chick;
    }

    @Override
    public Chicken incubationChick(Chick chick) {
        log.info("{} incubation process ... ", chick.toString());

        Util.delay(2000);
        Chicken chicken = new Chicken(chick.getId(), chick.getName());
        log.info("Transformation into {}", chicken.toString());
        return chicken;
    }
}
