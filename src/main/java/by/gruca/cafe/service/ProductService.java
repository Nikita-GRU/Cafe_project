package by.gruca.cafe.service;

import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts() throws ServiceException;


    Product getProductByName(String productName) throws ServiceException;


    void updateProduct(String productIdToUpdate, String newNameParam,
                       String newPriceParam, String newImageUriParam, String newDescriptionParam,
                       String newBonusParam) throws ServiceException;

    void addNewProduct(String nameParam, String priceParam,
                       String descriptionParam, String imageUriParam, String bonusParam, String categoryParam) throws ServiceException;

    List<Product> getProductsByCategory(String categoryParam) throws ServiceException;

    List<Product> getPaginatedProducts(int itemsPerPage, int pageNumber) throws ServiceException;

    int getProductsCount() throws ServiceException;
}
