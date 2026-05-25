package flow.utils;

public class Validator {
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidLength(String value, int minLength) {
        return value != null && value.trim().length() >= minLength;
    }
}
