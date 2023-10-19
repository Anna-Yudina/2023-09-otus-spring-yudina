package ru.otus.spring.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.config.TestConfig;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ResultServiceTest {

    TestConfig testConfig = Mockito.mock(TestConfig.class);

    @Test
    public void getRightAnswersCountToPassIsNegativeTest(){
        assertThat(testConfig.getRightAnswersCountToPass()).isNotNegative();
    }
}
