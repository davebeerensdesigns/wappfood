package com.wappstars.wappfood.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidMetaData {
    public static boolean isValidMetaKey(String metakey) {
        String regex = "^[a-zA-Z_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(metakey);
        boolean matches = matcher.matches();
        return matches;
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();
        return matches;
    }
    public static boolean isValidPhone(String phone) {
        String regex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        boolean matches = matcher.matches();
        return matches;
    }
    public static boolean isValidCompany(String company) {
        String regex = "^[a-zA-Z0-9 -_]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(company);
        boolean matches = matcher.matches();
        return matches;
    }
    public static boolean isValidAddress(String address) {
        String regex = "^(?=.*[0-9 ])(?=.*[a-zA-Z ])([a-zA-Z0-9 ]+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);
        boolean matches = matcher.matches();
        return matches;
    }
    public static boolean isValidCity(String city) {
        String regex = "^[a-zA-Z0-9 -]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(city);
        boolean matches = matcher.matches();
        return matches;
    }
    public static boolean isValidState(String state) {
        String regex = "^[a-zA-Z0-9 -]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(state);
        boolean matches = matcher.matches();
        return matches;
    }
    public static boolean isValidPostcode(String postcode) {
        String regex = "^[a-zA-Z0-9 -]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postcode);
        boolean matches = matcher.matches();
        return matches;
    }
    public static boolean isValidCountry(String country) {
        String regex = "^[a-zA-Z0-9 -]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(country);
        boolean matches = matcher.matches();
        return matches;
    }
}
