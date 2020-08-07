package by.gruca.cafe.util;

import by.gruca.cafe.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public enum RequestParameterConverter {
    INSTANCE;

    public PaymentType valueOfPaymentTypeParam(String paymentTypeParam) throws UtilException {
        return Arrays.stream(PaymentType.values())
                .filter(value -> value.getPayment().equals(paymentTypeParam))
                .findAny().orElseThrow(() -> new UtilException("Unacceptable payment parameter"));
    }

    public DeliveryType valueOfDeliveryTypeParam(String deliveryTypeParam) throws UtilException {
        return Arrays.stream(DeliveryType.values())
                .filter(value -> value.getDeliveryName().equals(deliveryTypeParam))
                .findAny().orElseThrow(() -> new UtilException("Unacceptable deliveryType parameter"));
    }

    public LocalDateTime valueOfLocalDateTimeParam(String deliveryDateParam) throws UtilException {
        try {
            return LocalDateTime.parse(deliveryDateParam);
        } catch (DateTimeParseException e) {
            throw new UtilException("Unacceptable dateTime parameter", e);
        }

    }

    public int valueOfInteger(String intParam) throws UtilException {
        try {
            return Integer.parseInt(intParam);
        } catch (NumberFormatException e) {
            throw new UtilException("Unacceptable int parameter");
        }
    }

    public int valueOfPositiveInteger(String intParam) throws UtilException {
        try {
            int value = Integer.parseInt(intParam);
            if (value <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new UtilException("Unacceptable positive int parameter");
        }
        return Integer.parseInt(intParam);
    }

    public long valueOfLong(String longParam) throws UtilException {
        try {
            return Long.parseLong(longParam);
        } catch (NumberFormatException e) {
            throw new UtilException("Unacceptable long parameter");
        }
    }

    public double valueOfDouble(String doubleParam) throws UtilException {
        try {
            return Double.parseDouble(doubleParam);
        } catch (NumberFormatException e) {
            throw new UtilException("Unacceptable double parameter");
        }
    }

    public BigDecimal valueOfBigDecimal(String bigDecimalParam) throws UtilException {
        try {
            return BigDecimal.valueOf(valueOfDouble(bigDecimalParam));
        } catch (NumberFormatException e) {
            throw new UtilException("Unacceptable long parameter");
        }
    }

    public Role valueOfRole(String roleParam) throws UtilException {
        return Arrays.stream(Role.values())
                .filter(r -> r.getRoleValue().equals(roleParam))
                .findAny().orElseThrow(() -> new UtilException("Unacceptable role parameter"));
    }

    public OrderStatus valueOfOrderStatus(String orderStatusParam) throws UtilException {
        return Arrays.stream(OrderStatus.values())
                .filter(value -> value.getStatus().equals(orderStatusParam))
                .findAny().orElseThrow(() -> new UtilException("Unacceptable orderType parameter"));
    }

    public Category valueOfCategory(String categoryParam) throws UtilException {
        return Arrays.stream(Category.values())
                .filter(value -> value.getCategory().equals(categoryParam))
                .findAny().orElseThrow(() -> new UtilException("Unacceptable category parameter"));
    }
}
