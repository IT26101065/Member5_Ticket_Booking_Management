package com.cinereserve.ticket.util;

public final class StringUtil {
    private StringUtil() {}
    public static String[] splitRecord(String line) {
        if (line == null) return new String[0];
        return line.split("\\|", -1);
    }
    public static String joinRecord(String... fields) {
        if (fields == null) return "";
        return String.join("|", fields);
    }
}