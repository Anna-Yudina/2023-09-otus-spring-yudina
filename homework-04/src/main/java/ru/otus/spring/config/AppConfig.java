package ru.otus.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "test")
public class AppConfig implements TestFileNameProvider, TestConfig, LocaleConfig {

    private Map<String, String> fileNameByLocaleTag;

    private int rightAnswersCountToPass;

    private Locale locale;

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }

    public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }
}
