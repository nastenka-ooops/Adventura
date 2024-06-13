package com.neotour.service;

import java.time.LocalDate;
import java.time.Month;

public class SeasonDetector {
    public static Season getCurrentSeason() {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();

        if (currentMonth == Month.DECEMBER || currentMonth == Month.JANUARY || currentMonth == Month.FEBRUARY) {
            return Season.WINTER;
        } else if (currentMonth == Month.MARCH || currentMonth == Month.APRIL || currentMonth == Month.MAY) {
            return Season.SPRING;
        } else if (currentMonth == Month.JUNE || currentMonth == Month.JULY || currentMonth == Month.AUGUST) {
            return Season.SUMMER;
        } else {
            return Season.AUTUMN;
        }
    }
}
