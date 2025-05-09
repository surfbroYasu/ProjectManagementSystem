package com.example.projectmanagement.users.utils;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.projectmanagement.users.domain.User;

public class FullNameFormatter {
    public static String format(User user, Locale locale) {
        if (user == null) return "";

        String firstName = nullToEmpty(user.getFirstName());
        String middleName = nullToEmpty(user.getMiddleName());
        String lastName = nullToEmpty(user.getLastName());

        if (Locale.JAPAN.equals(locale)) {
            return Stream.of(lastName, middleName, firstName)
                         .filter(s -> !s.isBlank())
                         .collect(Collectors.joining(" "));
        }

        return Stream.of(firstName, middleName, lastName)
                     .filter(s -> !s.isBlank())
                     .collect(Collectors.joining(" "));
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
