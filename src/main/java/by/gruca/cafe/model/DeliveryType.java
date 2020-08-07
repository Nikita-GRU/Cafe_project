package by.gruca.cafe.model;

public enum DeliveryType {
    SELF_DELIVERY("self_delivery"), DELIVERY("delivery");
    String deliveryName;

    DeliveryType(String delivery) {
        this.deliveryName = delivery;
    }

    public String getDeliveryName() {
        return deliveryName;
    }
}
