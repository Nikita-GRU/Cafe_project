package by.gruca.cafe.util;

import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.LongValidator;

public class Validator {

    public static final int NAME_MAX_LENGTH = 25;
    public static final int MAX_PASSWORD_LENGTH = 16;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_REVIEW_VALUE = 255;

    public static boolean validateEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean validateDouble(String value) {
        return DoubleValidator.getInstance().isValid(value);
    }

    public static boolean validateInt(String value) {
        return IntegerValidator.getInstance().isValid(value);
    }

    public static boolean validateBonuses(String value) {
        if (validateInt(value)) {
            int intValue = Integer.parseInt(value);
            return intValue >= 0;
        }
        return false;
    }

    public static boolean validateNames(String value) {
        return value.length() > 0 && value.length() < NAME_MAX_LENGTH;
    }

    public static boolean validatePrice(String value) {
        if (validateDouble(value)) {
            double price = Double.parseDouble(value);
            return price > 0;
        }
        return false;

    }

    public static boolean validateLong(String value) {
        return LongValidator.getInstance().isValid(value);
    }

    public static boolean validatePassword(String value) {
        return value.length() > MIN_PASSWORD_LENGTH && value.length() < MAX_PASSWORD_LENGTH;

    }

    public static boolean validateReview(String value) {
        return value.length() < MAX_REVIEW_VALUE;
    }
}
