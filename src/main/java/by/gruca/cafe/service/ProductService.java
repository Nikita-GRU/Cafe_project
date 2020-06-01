package by.gruca.cafe.service;

import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.List;

public interface ProductService {
    List<Product> getProducts() throws ServiceException;
}
