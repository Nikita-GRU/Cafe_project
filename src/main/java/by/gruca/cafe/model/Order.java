package by.gruca.cafe.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Order {
    private int id;
    private LocalDateTime date;
    private double price;
    private HashMap<Product, Integer> products;
    private String review;
    private Account account;
    private boolean isDelivered;
    private boolean isAccepted;

    public Order() {
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getId() != order.getId()) return false;
        if (Double.compare(order.getPrice(), getPrice()) != 0) return false;
        if (isDelivered() != order.isDelivered()) return false;
        if (isAccepted() != order.isAccepted()) return false;
        if (!getDate().equals(order.getDate())) return false;
        if (getProducts() != null ? !getProducts().equals(order.getProducts()) : order.getProducts() != null)
            return false;
        if (getReview() != null ? !getReview().equals(order.getReview()) : order.getReview() != null) return false;
        return getAccount().equals(order.getAccount());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        result = 31 * result + getDate().hashCode();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getProducts() != null ? getProducts().hashCode() : 0);
        result = 31 * result + (getReview() != null ? getReview().hashCode() : 0);
        result = 31 * result + getAccount().hashCode();
        result = 31 * result + (isDelivered() ? 1 : 0);
        result = 31 * result + (isAccepted() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", price=" + price +
                ", products=" + products +
                ", review='" + review + '\'' +
                ", account=" + account +
                ", isDelivered=" + isDelivered +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
