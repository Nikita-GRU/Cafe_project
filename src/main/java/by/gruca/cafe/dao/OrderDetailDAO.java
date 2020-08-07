package by.gruca.cafe.dao;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.OrderDetail;

import java.util.List;

public interface OrderDetailDAO extends AbstractDAO<OrderDetail> {
    List<OrderDetail> readAllByOrder(long orderId) throws DAOException;
}
