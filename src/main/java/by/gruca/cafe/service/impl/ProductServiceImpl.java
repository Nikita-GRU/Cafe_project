package by.gruca.cafe.service.impl;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.ProductService;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    Logger logger = LogManager.getLogger(AccountServiceImpl.class);

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
    public void updateProduct(Product newProduct, int productId) throws ServiceException {
        try {
            Product product = DAOFactory.INSTANCE.getProductDAO().read(productId).get();
            if (newProduct.getName() != null) {
                product.setName(newProduct.getName());
            }
            if (newProduct.getPrice() != 0.0) {
                product.setPrice(newProduct.getPrice());
            }
            if (newProduct.getDescription() != null) {
                product.setDescription(newProduct.getDescription());
            }
            if (newProduct.getImageUri() != null) {
                product.setImageUri(newProduct.getImageUri());
            }
            if (newProduct.getBonus() != 0) {
                product.setBonus(newProduct.getBonus());
            }

            DAOFactory.INSTANCE.getProductDAO().update(product);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Product getProductByName(String productName) throws ServiceException {
        Product product;
        try {
            product = DAOFactory.INSTANCE.getProductDAO().getProductByName(productName).get();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws ServiceException {
        List<Product> products;
        try {
            products = DAOFactory.INSTANCE.getProductDAO().getAllProducts();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return products;
    }
}
