package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW) ? "Сейчас" : date.format(DATE_FORMATTER);
    }

    public static LocalDate parse(String date) {
        if (HtmlUtil.isEmpty(date) || ("сейчас".equals(date.trim().toLowerCase()))) {
            return NOW;
        }
        YearMonth ym = YearMonth.parse(date, DATE_FORMATTER);
        return LocalDate.of(ym.getYear(), ym.getMonthValue(), 1);
    }
}
