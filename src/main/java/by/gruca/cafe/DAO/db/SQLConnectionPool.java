package by.gruca.cafe.DAO.db;


import by.gruca.cafe.DAO.config.ConnectionPoolConfig;
import by.gruca.cafe.DAO.exception.DAOException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


public enum SQLConnectionPool {
    INSTANCE;


    private int initConnections;
    private int maxConnections;
    private String url;
    private String user;
    private String password;
    private LinkedBlockingQueue<ConnectionProxy> connectionPool;
    private List<ConnectionProxy> usedConnections;


    SQLConnectionPool() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        connectionPool = new LinkedBlockingQueue<>();
        usedConnections = new ArrayList<>();
        createConnectionPool();

    }

    private ConnectionProxy createConnection() throws SQLException {

        return new ConnectionProxy(DriverManager.getConnection(url, user, password));
    }

    private void createConnectionPool() {
        initProperties();
        try {
            for (int i = 0; i < initConnections; i++) {
                connectionPool.add(createConnection());
            }
        } catch (SQLException e) {

        }
    }

    public ConnectionProxy getConnection() throws DAOException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxConnections) {
                try {
                    connectionPool.add(createConnection());
                } catch (SQLException e) {

                    throw new DAOException("Connection creating error", e);
                }
            } else {
                throw new DAOException("Maximum pool size reached, no available connections!", new RuntimeException());
            }
        }
        ConnectionProxy connection = connectionPool.poll();
        usedConnections.add(connection);
     /*  logger.info("GETOONN");
        logger.info("conpool = " + connectionPool.size());
        logger.info("usedcon = " + usedConnections.size());*/
        return connection;
    }

    public void releaseConnection(ConnectionProxy connection) {
        usedConnections.remove(connection);
        connectionPool.add(connection);
     /*  logger.info("OUTCONN");
       logger.info("conpool = " + connectionPool.size());
       logger.info("usedcon = " + usedConnections.size());*/
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    private LinkedBlockingQueue<ConnectionProxy> getConnectionPoolConnections() {
        return connectionPool;
    }


    public void shutdown() throws DAOException {

        try {
            for (int i = 0; i < usedConnections.size(); i++) {
                usedConnections.get(i).getConnection().close();
            }
            for (int i = 0; i < connectionPool.size(); i++) {
                connectionPool.peek().getConnection().close();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        usedConnections.clear();
        connectionPool.clear();
    }

    public void initProperties() {
        initConnections = Integer.parseInt(ConnectionPoolConfig.getProperty("init_size"));
        maxConnections = Integer.parseInt(ConnectionPoolConfig.getProperty("max_size"));
        url = ConnectionPoolConfig.getProperty("db_url");
        user = ConnectionPoolConfig.getProperty("db_user");
        password = ConnectionPoolConfig.getProperty("db_password");
    }

}

