package by.gruca.cafe.service.impl;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.dao.impl.ProductDAOImpl;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.ProductService;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> getProducts() throws ServiceException {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        List<Product> products = new ArrayList();

        try {
            products = productDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("get products service exception", e);
        }
        return products;
    }
}
