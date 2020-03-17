package DAO.repository.impl;

import DAO.exception.DAOException;
import DAO.repository.OrderRepository;
import model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    private static final String SQL_GET = "select * from order where id=(?)";
    private static final String SQL_GET_ALL = "select * from order";
    private static final String SQL_SAVE = "insert into order(id,date,total_price,bonus_points,payment_id,review,account_id) " +
            "values(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update order set id=?,date=?,total_price=?,bonus_points=?,payment_id=?,review=?,account_id=?" +
            "where id=?";
    private static final String SQL_DELETE = "delete from order where id=?";
    Logger logger = LogManager.getLogger(AccountRepositoryImpl.class);

    @Override
    public Optional<Order> get(int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() throws DAOException {
        return null;
    }

    @Override
    public void save(Order order) throws DAOException {

    }

    @Override
    public void update(Order order) throws DAOException {

    }

    @Override
    public void delete(Order order) throws DAOException {

    }
}
