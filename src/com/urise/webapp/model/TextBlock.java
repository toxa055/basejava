package com.urise.webapp.model;

import java.time.YearMonth;

public class TextBlock {
    private YearMonth yearMonthSince;
    private YearMonth yearMonthTo;
    private String place;
    private String position;
    private String description;

    public TextBlock(int yearSince, int monthSince, int yearTo, int monthTo, String place, String position, String description) {
        yearMonthSince = YearMonth.of(yearSince, monthSince);
        yearMonthTo = YearMonth.of(yearTo, monthTo);
        this.place = place;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return yearMonthSince + " / "
                + yearMonthTo + ": "
                + place + ". "
                + (position == null ? "" : position + ". ")
                + description;
    }
}
