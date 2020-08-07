package by.gruca.cafe.model;

public class OrderDetail {
    private long orderId;
    private Product product;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(long orderId, Product product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
