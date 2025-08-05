package com.example.projectmanagement.application.util;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.projectmanagement.users.datastructure.entity.UserEntity;

public class FullNameFormatter {
    public static String formatByUser(UserEntity user, String lang) {
        if (user == null) return "";

        String firstName = nullToEmpty(user.getFirstName());
        String middleName = nullToEmpty(user.getMiddleName());
        String lastName = nullToEmpty(user.getLastName());

        return formatter(firstName, middleName, lastName, lang);
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    public static String formatter(String firstName, String middleName, String lastName, String lang) {

        if (Locale.JAPAN.getLanguage().equals(lang)) {
            return Stream.of(lastName, middleName, firstName)
                         .filter(s -> !s.isBlank())
                         .collect(Collectors.joining(" "));
        }

        return Stream.of(firstName, middleName, lastName)
                     .filter(s -> !s.isBlank())
                     .collect(Collectors.joining(" "));
    }


}


