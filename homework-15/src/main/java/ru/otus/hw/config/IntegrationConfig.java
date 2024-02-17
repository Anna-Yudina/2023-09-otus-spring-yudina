package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.hw.models.Chick;
import ru.otus.hw.models.Egg;
import ru.otus.hw.services.IncubatorService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> eggChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> chickenChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow chickenFlow(IncubatorService incubatorService) {
        return IntegrationFlow.from(eggChannel())
                .split()
                .<Egg>filter(egg -> !egg.getName().equals("bad"))
                .handle(incubatorService, "incubationEgg")
                .<Chick, Chick>transform(chick -> new Chick(chick.getId(), chick.getName().toUpperCase()))
                .handle(incubatorService, "incubationChick")
                .aggregate(a -> a.groupTimeout(10000).sendPartialResultOnExpiry(true))
                .channel(chickenChannel())
                .get();
    }
}
