package ru.otus.spring.exam.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Data
public class LocaleProperty {

    private Locale locale;
}
