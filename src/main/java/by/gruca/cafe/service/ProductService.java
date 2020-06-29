package by.gruca.cafe.service;

import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts() throws ServiceException;

    void addNewProduct(Product newProduct) throws ServiceException;

    void updateProduct(Product product, int productId) throws ServiceException;

    Product getProductByName(String productName) throws ServiceException;


}
