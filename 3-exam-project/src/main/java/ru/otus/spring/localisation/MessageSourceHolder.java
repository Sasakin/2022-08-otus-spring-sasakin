package ru.otus.spring.localisation;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.property.LocaleProperty;

@Component
@AllArgsConstructor
public class MessageSourceHolder {
    private LocaleProperty localeProperty;
    private MessageSource messageSource;

    public String getMessage(String msgCode, String defaultMsg) {
        return messageSource.getMessage(msgCode, new String[]{defaultMsg}, localeProperty.getLocale());
    }
}
