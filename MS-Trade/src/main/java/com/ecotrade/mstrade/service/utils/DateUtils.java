package com.ecotrade.mstrade.service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateUtils {

    public static String formatToReport(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static String formatToReport(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        return formatter.format(date);
    }

    public static List<String> getMonthsByStart(LocalDate startDate) {
        int start = startDate.getMonthValue();
        List<String> results = new ArrayList<>();
        for (int i = start; i <= 12; i++) {
            results.add(Month.of(i).getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        }
        for (int i = 1; i < start; i++) {
            results.add(Month.of(i).getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        }
        return results;
    }
}
