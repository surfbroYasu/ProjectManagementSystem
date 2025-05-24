package com.example.projectmanagement.generalutil;

public class CaseConverter {

    public static String toCamelCase(String input) {
        return toCamelOrPascal(input, false);
    }

    public static String toPascalCase(String input) {
        return toCamelOrPascal(input, true);
    }

    private static String toCamelOrPascal(String input, boolean pascal) {
        if (input == null || input.isEmpty()) return input;
        StringBuilder result = new StringBuilder();
        boolean nextUpper = pascal;
        for (char c : input.toCharArray()) {
            if (c == '_' || c == '-' || c == ' ') {
                nextUpper = true;
            } else {
                result.append(nextUpper ? Character.toUpperCase(c) : Character.toLowerCase(c));
                nextUpper = false;
            }
        }
        return result.toString();
    }

    public static String toSnakeCase(String input) {
        if (input == null || input.isEmpty()) return input;
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append('_').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.charAt(0) == '_' ? result.substring(1) : result.toString();
    }

    public static String toUpperSnakeCase(String input) {
        return toSnakeCase(input).toUpperCase();
    }

    public static String toKebabCase(String input) {
        return toSnakeCase(input).replace('_', '-');
    }
}
