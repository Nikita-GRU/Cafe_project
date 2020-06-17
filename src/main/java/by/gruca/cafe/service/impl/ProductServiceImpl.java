package by.gruca.cafe.service.impl;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.dao.impl.ProductDAOImpl;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.ProductService;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    Logger logger = LogManager.getLogger(AccountServiceImpl.class);

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

    @Override
    public void addNewProduct(Product newProduct) throws ServiceException {
        try {
            DAOFactory.INSTANCE.getProductDAO().create(newProduct);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateProduct(Product product) throws ServiceException {
        try {
            DAOFactory.INSTANCE.getProductDAO().update(product);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Product getProductByName(String productName) throws ServiceException {
        Product product = new Product();
        try {
            product = DAOFactory.INSTANCE.getProductDAO().read(productName).get();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return product;
    }
}
