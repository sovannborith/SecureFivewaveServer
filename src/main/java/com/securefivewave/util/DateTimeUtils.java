package com.securefivewave.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateTimeUtils {

    public static final String ASIA_PHNOM_PENH = "Asia/Phnom_Penh";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYY = "yyyy";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ssXXX")
            .withZone(ZoneId.of(ASIA_PHNOM_PENH));

    private DateTimeUtils() {
        // nothing
    }

    public static String getDateTime() {
        return FORMATTER.format(new Date().toInstant());
    }

    public static String getDateTime(Date date) {
        if (date == null) {
            return "-";
        }
        return FORMATTER.format(date.toInstant());
    }

    public static String getCurrentDateTime(String pattern) {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of(ASIA_PHNOM_PENH));
        return format.format(new Date().toInstant());
    }

    public static String getDateFormat(Date date, String pattern) {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of(ASIA_PHNOM_PENH));
        return format.format(date.toInstant());
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {

        return (localDateTime != null) ? Date.from(localDateTime.atZone(ZoneId.of(ASIA_PHNOM_PENH)).toInstant()) : null;
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of(ASIA_PHNOM_PENH)).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of(ASIA_PHNOM_PENH)).toLocalDateTime();
    }

}