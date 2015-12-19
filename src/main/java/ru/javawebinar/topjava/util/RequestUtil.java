package ru.javawebinar.topjava.util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class RequestUtil {
    private static int toInt(HttpServletRequest request, String name) {
        return Integer.valueOf(Objects.requireNonNull(request.getParameter(name)));
    }

    public static int getId(HttpServletRequest request) {
        return toInt(request, "id");
    }

    public static LocalDate parseDate(LocalDate ld, String date) {
        return (date != null && date.length() > 0) ? LocalDate.parse(date) : LocalDate.of(ld.getYear(), ld.getMonth(), ld.getDayOfMonth());
    }

    public static LocalTime parseTime(LocalTime lt, String time) {
        return (time != null && time.length() > 0) ? LocalTime.parse(time) : LocalTime.of(lt.getHour(), lt.getMinute());
    }
}
