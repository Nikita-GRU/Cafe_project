package by.gruca.cafe.util;

import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

public class Validator {
    public static boolean validateEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean validateDouble(String value) {
        return DoubleValidator.getInstance().isValid(value);
    }

    public static boolean validateInt(String value) {
        return IntegerValidator.getInstance().isValid(value);
    }
}
