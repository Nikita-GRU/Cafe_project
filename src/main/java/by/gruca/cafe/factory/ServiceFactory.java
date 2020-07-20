package by.gruca.cafe.factory;

import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.OrderService;
import by.gruca.cafe.service.ProductService;
import by.gruca.cafe.service.impl.AccountServiceImpl;
import by.gruca.cafe.service.impl.OrderServiceImpl;
import by.gruca.cafe.service.impl.ProductServiceImpl;

public enum ServiceFactory {
    INSTANCE;
    private final ProductService productService;
    private final AccountService accountService;
    private final OrderService orderService;

    ServiceFactory() {
        productService = new ProductServiceImpl();
        accountService = new AccountServiceImpl();
        orderService = new OrderServiceImpl();
    }

    public ProductService getProductService() {
        return productService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
