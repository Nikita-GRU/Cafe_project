package by.gruca.cafe.model;

public enum PaymentType {
    CASH(1, "cash"),
    BALANCE(2, "balance");

    int id;
    String payment;

    PaymentType(int id, String paymentType) {
        this.id = id;
        this.payment = paymentType;
    }

    public int getId() {
        return id;
    }

    public String getPayment() {
        return payment;
    }
}

