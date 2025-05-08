package cl.ntt.userapi.user_api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Utils {

    @Value("${cl.password.regex}")
    private String passwordRegexProperty;

    @Value("${cl.email.regex}")
    private String emailRegexProperty;

    @Value("${cl.date.format}")
    private String dateFormatProperty;

    private static String passwordRegex;
    private static String emailRegex;
    private static String dateFormat;

    @PostConstruct
    public void init() {
        passwordRegex = passwordRegexProperty;
        emailRegex = emailRegexProperty;
        dateFormat = dateFormatProperty;
    }

    public static boolean isValidEmail(String email) {
        return email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(passwordRegex);
    }

    public static String formattedDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return date.format(formatter);
    }

}
