package by.gruca.cafe.service;


import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.List;

public interface OrderService {
     Order createOrder(List<Product> products) throws ServiceException;

}
