package by.gruca.cafe.util;

import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;

public class Main {
    public static void main(String[] args) {
        try {
            Product product = ServiceFactory.INSTANCE.getProductService().getProductByName("meat");
            System.out.println(product + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        } catch (ServiceException e) {
            System.out.println(e);
        }

    }
}
