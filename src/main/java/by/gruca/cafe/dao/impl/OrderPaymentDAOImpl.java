package by.gruca.cafe.dao.impl;

import by.gruca.cafe.util.ModelMapper;
import by.gruca.cafe.dao.OrderPaymentDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.PaymentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderPaymentDAOImpl implements OrderPaymentDAO {
    public static final String SQL_STATEMENT_ERROR = "SQL statement error";
    public static final String ORDER_PAYMENT_PAYMENT_TYPE = "payment_type";
    public static final String ORDER_PAYMENT_ID = "order_payment_id";
    private static final String SQL_READ = "SELECT * FROM order_payment WHERE order_payment_id=? ";
    private static final String SQL_READ_ALL = "SELECT * from order_payment";
    private static final String SQL_CREATE = "INSERT INTO order_payment (order_payment_id,payment_type) " +
            "VALUES (DEFAULT,?)";
    private static final String SQL_UPDATE = "UPDATE order_payment SET payment_type=? WHERE order_payment_id=?";
    private static final String SQL_DELETE = "DELETE FROM order_payment WHERE order_payment_id=? ";
    private static final long CREATE_ERROR = -1;
    Logger logger = LogManager.getLogger(OrderPaymentDAOImpl.class);

    @Override
    public long create(PaymentType entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getPayment());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return CREATE_ERROR;

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public Optional<PaymentType> read(long id) throws DAOException {
        PaymentType paymentType = null;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ)) {
            readStatement.setLong(1, id);

            paymentType = buildPaymentType(readStatement);

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(paymentType);

    }


    @Override
    public int update(PaymentType entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getPayment());
            statement.setLong(2, entity.getId());
            int rowsAffected = statement.executeUpdate();
            return statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public boolean delete(PaymentType entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, entity.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public List<PaymentType> readAll() throws DAOException {
        List<PaymentType> paymentTypeList = new ArrayList<>();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL);) {
            paymentTypeList = buildPaymentTypeList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return paymentTypeList;
    }


    private PaymentType buildPaymentType(PreparedStatement readStatement) throws DAOException {
        PaymentType paymentType = null;
        try (ResultSet rs = readStatement.executeQuery()) {
            if (rs.next()) {
                paymentType = ModelMapper.INSTANCE.getPaymentTypeFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return paymentType;
    }

    private List<PaymentType> buildPaymentTypeList(PreparedStatement readStatement) throws DAOException {
        List<PaymentType> paymentTypeList = new ArrayList<>();
        PaymentType paymentType;
        try (ResultSet rs = readStatement.executeQuery()) {
            while (rs.next()) {
                paymentType = ModelMapper.INSTANCE.getPaymentTypeFromResultSet(rs);
                paymentTypeList.add(paymentType);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return paymentTypeList;
    }
}
