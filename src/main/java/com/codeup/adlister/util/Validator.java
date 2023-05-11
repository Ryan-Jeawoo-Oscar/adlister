package com.codeup.adlister.util;

import java.util.regex.Pattern;

public class Validator {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{2,15}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{4,10}$";

    private static final Pattern USERNAME_REGEX = Pattern.compile(USERNAME_PATTERN);
    private static final Pattern EMAIL_REGEX = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern PASSWORD_REGEX = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValidUsername(String username) {
        return USERNAME_REGEX.matcher(username).matches();
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return PASSWORD_REGEX.matcher(password).matches();
    }
}


