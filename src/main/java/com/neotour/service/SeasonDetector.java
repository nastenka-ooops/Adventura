package com.neotour.service;

import com.neotour.enums.Season;

import java.time.LocalDate;
import java.time.Month;

public class SeasonDetector {
    public static Season getCurrentSeason() {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        return switch (currentMonth) {
            case DECEMBER, JANUARY, FEBRUARY -> Season.WINTER;
            case MARCH, APRIL, MAY -> Season.SPRING;
            case JUNE, JULY, AUGUST -> Season.SUMMER;
            case SEPTEMBER, OCTOBER, NOVEMBER -> Season.AUTUMN;
        };
    }
}
