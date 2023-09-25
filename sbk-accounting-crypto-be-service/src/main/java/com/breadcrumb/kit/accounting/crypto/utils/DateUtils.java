package com.breadcrumb.kit.accounting.crypto.utils;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public final class DateUtils {

    private DateUtils() {}

    public static long monthsBetweenDates(long fromEpoch, long toEpoch) {
        LocalDateTime fromLDT = LocalDateTime.ofEpochSecond(fromEpoch, 0, ZoneOffset.UTC);
        LocalDateTime toLDT = LocalDateTime.ofEpochSecond(toEpoch, 0, ZoneOffset.UTC);

        return ChronoUnit.MONTHS.between(fromLDT.toLocalDate(), toLDT.toLocalDate());
    }
}
