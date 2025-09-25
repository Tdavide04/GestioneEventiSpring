package com.demo.eventi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component("dateUtils")
public class DateUtils {
    private static final String DEFAULT_PATTERN = "dd/MM/yyyy HH:mm";

    public String format(LocalDateTime date, String pattern) {
        if (date == null) return "";
        if (pattern == null || pattern.isBlank()) {
            pattern = DEFAULT_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    public String format(LocalDateTime date) {
        return format(date, DEFAULT_PATTERN);
    }

}

