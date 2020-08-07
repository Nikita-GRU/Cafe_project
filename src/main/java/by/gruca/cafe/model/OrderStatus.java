package by.gruca.cafe.model;

public enum OrderStatus {
    ORDERED(1, "ordered"),
    ACCEPTED(2, "accepted"),
    PAID(3, "paid"),
    DELIVERED(4, "delivered"),
    DECLINES(5, "declined");

    int id;
    String status;

    OrderStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
