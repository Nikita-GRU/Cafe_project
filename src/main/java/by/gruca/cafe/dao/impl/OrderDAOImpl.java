package by.gruca.cafe.dao.impl;


import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.dao.OrderDAO;
import by.gruca.cafe.model.Order;


import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    private static final String SQL_GET = "select * from order where id=(?)";
    private static final String SQL_GET_ALL = "select * from order";
    private static final String SQL_SAVE = "insert into order(id,date,total_price,bonus_points,payment_id,review,account_id) " +
            "values(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update order set id=?,date=?,total_price=?,bonus_points=?,payment_id=?,review=?,account_id=?" +
            "where id=?";
    private static final String SQL_DELETE = "delete from order where id=?";
    private static final String SQL_GET_ACCOUNT = "select id from order where id=?";
    private static final String SQL_GET_PRODUCTS = "select product_id from order join product on order.product_id=product.id where order.id=?";
    //Logger logger = LogManager.getLogger(AccountDAO.class);

    @Override
    public boolean create(Order model) throws DAOException {
        return false;
    }

    @Override
    public Optional<Order> read(Integer integer) throws DAOException {
        return Optional.empty();
    }

    @Override
    public int update(Order model) throws DAOException {
        return 0;
    }

    @Override
    public boolean delete(Order model) throws DAOException {
        return false;
    }

    @Override
    public List<Order> getAll() throws DAOException {
        return null;
    }
//    @Override
//    public Optional<Order> get(int id) throws DAOException {
//
//        Order order = new Order();
//        List<Product> products = new ArrayList<>();
//        ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
//        try {
//            PreparedStatement statement = connection.prepareStatement(SQL_GET);
//            statement.setInt(1, (int) id);
//            ResultSet rs = statement.executeQuery();
//            rs.next();
//            order.setOrderDate(rs.getNString(2));
//            order.setTotalPrice(rs.getInt(3));
//            order.setBonusPoints(rs.getInt(4));
//            //order.setPaymentMethod(new PaymentMethodImpl().get(rs.getInt(5)));
//            order.setReview(rs.getNString(6));
//         //   order.setAccount(new AccountDAO().get(rs.getInt(7)).get());
//
//            // use product repository to generate list of products!
//        } catch (SQLException e) {
//            logger.error(e);
//            throw new DAOException("SQL statement error", e);
//        } finally {
//            connection.close();
//        }
//        return Optional.of(order);
//    }
//
//    @Override
//    public List<Order> getAll() throws DAOException {
//        List<Order> resultCollection = new ArrayList<>();
//        ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
//        if (Objects.isNull(connection)) {
//            throw new RuntimeException("No connection");
//        }
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                Order receivedUser = new Order();
//
//            }
//            private String orderDate;
//            private int totalPrice;
//            private int bonusPoints;
//            private List<Product> products;
//            private String review;
//            private Account account;
//            private PaymentMethod paymentMethod;
    //
}
