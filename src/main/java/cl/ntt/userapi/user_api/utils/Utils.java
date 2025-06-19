package cl.ntt.userapi.user_api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Utils {

    @Value("${cl.date.format}")
    private String dateFormatProperty;
    private static String dateFormat;

    @PostConstruct
    public void init() {
        dateFormat = dateFormatProperty;
    }

    public static String formattedDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return date.format(formatter);
    }

}
