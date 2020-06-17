package by.gruca.cafe.factory;

import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.ProductService;
import by.gruca.cafe.service.impl.AccountServiceImpl;
import by.gruca.cafe.service.impl.ProductServiceImpl;

public enum ServiceFactory {
    INSTANCE;
    private ProductService productService;
    private AccountService accountService;

    ServiceFactory() {
        productService = new ProductServiceImpl();
        accountService = new AccountServiceImpl();
    }

    public ProductService getProductService() {
        return productService;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
