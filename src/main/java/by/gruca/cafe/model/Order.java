package by.gruca.cafe.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class Order {
    private long id;
    private LocalDateTime creationDate;
    private LocalDateTime deliveryDate;
    private BigDecimal price;
    private String note;
    private String feedback;
    private Account account;
    private Map<Product, Integer> products;
    private OrderStatus orderStatus;
    private int score;
    private PaymentType paymentType;
    private String address;
    private String apartment;
    private int bonusToPay;

    public int getBonusToPay() {
        return bonusToPay;
    }

    public void setBonusToPay(int bonusToPay) {
        this.bonusToPay = bonusToPay;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", deliveryDate=" + deliveryDate +
                ", price=" + price +
                ", note='" + note + '\'' +
                ", feedback='" + feedback + '\'' +
                ", account=" + account +
                ", orderDetails=" + products +
                ", orderStatus=" + orderStatus +
                ", score=" + score +
                ", paymentType=" + paymentType +
                '}';
    }
}
