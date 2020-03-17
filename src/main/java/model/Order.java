package model;

import java.util.Date;
import java.util.List;

public class Order {
    private Date orderDate;
    private int totalPrice;
    private int bonusPoints;
    private List<Product> products;
    private String review;

    public Order(Date orderDate, int totalPrice, int bonusPoints, List<Product> products, String review) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.bonusPoints = bonusPoints;
        this.products = products;
        this.review = review;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
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
}
