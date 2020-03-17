import DAO.db.ConnectionProxy;
import DAO.db.SQLConnectionPool;
import DAO.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        SQLConnectionPool connectionPool = SQLConnectionPool.INSTANCE;
        try {
            ConnectionProxy connection = connectionPool.getConnection();
            ConnectionProxy connection1 = connectionPool.getConnection();
            ConnectionProxy connection2 = connectionPool.getConnection();

            connectionPool.shutdown();

            PreparedStatement preparedStatement = connection1.prepareStatement("select *");
            ConnectionProxy сonnection3 = connectionPool.getConnection();

            System.out.println(connectionPool.getSize());
        } catch (DAOException | SQLException e) {
            e.printStackTrace();
        }

        //   System.out.println(ConnectionPoolProperties.INSTANCE.toString());


    }
}
