package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Chicken;
import ru.otus.hw.models.Egg;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;
import ru.otus.hw.util.Util;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChickenFarmServiceImpl implements ChickenFarmService {

    private static final String[] QUALITY = {"very well", "good", "satisfactory", "bad"};

    private static AtomicInteger idSequence = new AtomicInteger(0);

    private final IncubatorGateway incubatorGateway;

    @Override
    public void startProduction() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();

        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            commonPool.execute(() -> {
                List<Egg> eggs = generateEggs();
                log.info("{}, New eggs: {}", num, eggs.stream().map(Egg::toString)
                        .collect(Collectors.joining(",")));

                List<Chicken> chickens = incubatorGateway.process(eggs);
                if (chickens != null) {
                    log.info("{}, Ready chickens: {}", num, chickens.stream().map(Chicken::toString)
                            .collect(Collectors.joining(",")));
                } else {
                    log.info("chicks = null!!!!!!!!!!!");
                }
            });
            Util.delay(7000);
        }
    }

    private static List<Egg> generateEggs() {
        List<Egg> eggs = new ArrayList<>();

        for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
            eggs.add(generateEgg());
        }
        return eggs;
    }

    private static Egg generateEgg() {
        return new Egg(getNewId(), QUALITY[RandomUtils.nextInt(0, QUALITY.length)]);
    }

    private static int getNewId() {
        return idSequence.addAndGet(1);
    }
}
