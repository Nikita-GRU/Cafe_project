package by.gruca.cafe.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private LocalDateTime date;
    private double price;
    private List<Product> products;
    private String review;
    private Account account;

    public Order() {
        date = LocalDateTime.now();
        products = new ArrayList<>();
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public void addNewProduct(Product product) {
        products.add(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getId() != order.getId()) return false;
        if (Double.compare(order.getPrice(), getPrice()) != 0) return false;
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
                '}';
    }
}
