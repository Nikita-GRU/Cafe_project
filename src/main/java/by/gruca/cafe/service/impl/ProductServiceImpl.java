package by.gruca.cafe.service.impl;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Category;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.ProductService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.RequestParameterConverter;
import by.gruca.cafe.util.UtilException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Override
    public void updateProduct(String productIdToUpdateParam, String newNameParam, String newPriceParam, String newImageUriParam, String newDescriptionParam, String newBonusParam) throws ServiceException {
        try {
            long productId = RequestParameterConverter.INSTANCE.valueOfLong(productIdToUpdateParam);
            BigDecimal newPrice = RequestParameterConverter.INSTANCE.valueOfBigDecimal(newPriceParam);
            int newBonus = RequestParameterConverter.INSTANCE.valueOfInteger(newBonusParam);
            Product product = DAOFactory.INSTANCE.getProductDAO().read(productId)
                    .orElseThrow(() -> new ServiceException("Product not found"));
            product.setPrice(newPrice);
            product.setBonus(newBonus);
            product.setName(newNameParam);
            product.setImageUri(newImageUriParam);
            product.setDescription(newDescriptionParam);
            DAOFactory.INSTANCE.getProductDAO().update(product);

        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> getProductsByCategory(String categoryParam) throws ServiceException {
        List<Product> products;

        try {
            Category category = RequestParameterConverter.INSTANCE.valueOfCategory(categoryParam);
            products = DAOFactory.INSTANCE.getProductDAO().readAllByCategory(category);
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return products;
    }

    @Override
    public void addNewProduct(String nameParam, String priceParam, String descriptionParam, String imageUriParam, String bonusParam, String categoryParam) throws ServiceException {
        try {
            BigDecimal price = RequestParameterConverter.INSTANCE.valueOfBigDecimal(priceParam);
            int bonus = RequestParameterConverter.INSTANCE.valueOfInteger(bonusParam);
            Category category = RequestParameterConverter.INSTANCE.valueOfCategory(categoryParam);
            Product product = new Product();
            product.setCategory(category);
            product.setName(nameParam);
            product.setDescription(descriptionParam);
            product.setPrice(price);
            product.setImageUri(imageUriParam);
            product.setBonus(bonus);

            DAOFactory.INSTANCE.getProductDAO().create(product);
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Product getProductByName(String productName) throws ServiceException {
        Product product;
        try {
            product = DAOFactory.INSTANCE.getProductDAO().readByName(productName).orElseThrow(() -> new ServiceException("Product not found"));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws ServiceException {
        List<Product> products;
        try {
            products = DAOFactory.INSTANCE.getProductDAO().readAll();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return products;
    }

    @Override
    public List<Product> getPaginatedProducts(int itemsPerPage, int pageNumber) throws ServiceException {
        List<Product> products;
        try {
            products = DAOFactory.INSTANCE.getProductDAO().readAll(itemsPerPage, pageNumber);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return products;
    }

    @Override
    public int getProductsCount() throws ServiceException {
        int productsCount;
        try {
            productsCount = DAOFactory.INSTANCE.getProductDAO().readProductCount();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return productsCount;
    }
}
