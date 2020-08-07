package by.gruca.cafe.util;

import by.gruca.cafe.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.gruca.cafe.dao.impl.AccountDAOImpl.*;
import static by.gruca.cafe.dao.impl.CategoryDAOImpl.CATEGORY_CATEGORY_NAME;
import static by.gruca.cafe.dao.impl.OrderDAOImpl.*;
import static by.gruca.cafe.dao.impl.OrderDetailDAOImpl.*;
import static by.gruca.cafe.dao.impl.OrderPaymentDAOImpl.ORDER_PAYMENT_PAYMENT_TYPE;
import static by.gruca.cafe.dao.impl.OrderStatusDAOImpl.ORDER_STATUS_STATUS;
import static by.gruca.cafe.dao.impl.ProductDAOImpl.*;
import static by.gruca.cafe.dao.impl.RoleDAOImpl.ROLE_ROLE_NAME;

public enum ModelMapper {
    INSTANCE;


    ModelMapper() {
    }

    public Role getRoleFromResultSet(ResultSet rs) throws SQLException {
        Role role;
        String roleName = rs.getString(ROLE_ROLE_NAME);
        role = Arrays.stream(Role.values())
                .filter(value -> value.getRoleValue().equals(roleName))
                .findFirst()
                .orElse(Role.GUEST);
        return role;
    }

    public OrderStatus getOrderStatusFromResultSet(ResultSet rs) throws SQLException {
        OrderStatus orderStatus;
        String statusName = rs.getString(ORDER_STATUS_STATUS);
        orderStatus = Arrays.stream(OrderStatus.values())
                .filter(value -> value.getStatus().equals(statusName))
                .findFirst()
                .orElse(OrderStatus.ORDERED);
        return orderStatus;
    }

    public PaymentType getPaymentTypeFromResultSet(ResultSet rs) throws SQLException {
        PaymentType paymentType;
        String paymentName = rs.getString(ORDER_PAYMENT_PAYMENT_TYPE);
        paymentType = Arrays.stream(PaymentType.values())
                .filter(value -> value.getPayment().equals(paymentName))
                .findFirst()
                .orElse(PaymentType.CASH);
        return paymentType;
    }

    public Category getCategoryFromResultSet(ResultSet rs) throws SQLException {
        Category category;
        String categoryName = rs.getString(CATEGORY_CATEGORY_NAME);
        category = Arrays.stream(Category.values())
                .filter(value -> value.getCategory().equals(categoryName))
                .findFirst()
                .orElse(Category.ALL);
        return category;
    }

    public Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong(ACCOUNT_ID));
        account.setRole(getRoleFromResultSet(rs));
        account.setEmail(rs.getNString(ACCOUNT_EMAIL));
        account.setPassword(rs.getNString(ACCOUNT_PASSWORD));
        account.setFirstName(rs.getNString(ACCOUNT_FIRST_NAME));
        account.setPhoneNumber(rs.getString(ACCOUNT_PHONE_NUMBER));
        account.setBalance(rs.getBigDecimal(ACCOUNT_BALANCE));
        account.setBonus(rs.getInt(ACCOUNT_BONUS));
        return account;
    }

    public Product getProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt(PRODUCT_ID));
        product.setCategory(getCategoryFromResultSet(rs));
        product.setName(rs.getString(PRODUCT_NAME));
        product.setPrice(rs.getBigDecimal(PRODUCT_PRICE));
        product.setDescription(rs.getString(PRODUCT_DESCRIPTION));
        product.setImageUri(rs.getString(PRODUCT_IMAGE_URI));
        product.setBonus(rs.getInt(PRODUCT_BONUS));
        return product;
    }

    public OrderDetail getOrderDetailFromResultSet(ResultSet rs) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(rs.getLong(ORDER_DETAIL_ORDER_ID));
        orderDetail.setProduct(getProductFromResultSet(rs));
        orderDetail.setQuantity(rs.getInt(ORDER_DETAIL_QUANTITY));
        return orderDetail;
    }

    public Order getOrderFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong(ORDER_ID));
        order.setAccount(getAccountFromResultSet(rs));
        order.setDeliveryDate(TimeConverter.convertFromSQLDateTimeToLocalDateTime(rs.getString(ORDER_DELIVERY_DATE)));
        order.setCreationDate(TimeConverter.convertFromSQLDateTimeToLocalDateTime(rs.getString(ORDER_CREATION_DATE)));
        order.setPrice(rs.getBigDecimal(ORDER_PRICE));
        order.setFeedback(rs.getNString(ORDER_FEEDBACK));
        order.setOrderStatus(getOrderStatusFromResultSet(rs));
        order.setNote(rs.getString(ORDER_NOTE));
        order.setScore(rs.getShort(ORDER_SCORE));
        order.setPaymentType(getPaymentTypeFromResultSet(rs));
        order.setAddress(rs.getString(ORDER_ADDRESS));
        order.setApartment(rs.getString(ORDER_APARTMENT));
        order.setBonusToPay(rs.getInt(ORDER_BONUS_TO_PAY));
        return order;
    }

}
