package DAO.db;

import DAO.config.ConnectionPoolProperties;
import DAO.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


public enum SQLConnectionPool {
    INSTANCE;

    Logger logger = LogManager.getLogger(SQLConnectionPool.class);
    private int initConnections;
    private int maxConnections;
    private String url;
    private String user;
    private String password;
    private LinkedBlockingQueue<ConnectionProxy> connectionPool;
    private List<ConnectionProxy> usedConnections;


    SQLConnectionPool() {
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
            logger.error(e);
        }
    }

    public ConnectionProxy getConnection() throws DAOException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxConnections) {
                try {
                    connectionPool.add(createConnection());
                } catch (SQLException e) {
                    logger.error(e);
                    throw new DAOException("Connection creating error", e);
                }
            } else {
                throw new DAOException("Maximum pool size reached, no available connections!", new RuntimeException());
            }
        }
        ConnectionProxy connection = connectionPool.poll();
        usedConnections.add(connection);
//        logger.info("GETOONN");
//        logger.info("conpool = " + connectionPool.size());
//        logger.info("usedcon = " + usedConnections.size());
        return connection;
    }

    public void releaseConnection(ConnectionProxy connection) {
        usedConnections.remove(connection);
        connectionPool.add(connection);
//        logger.info("OUTCONN");
//        logger.info("conpool = " + connectionPool.size());
//        logger.info("usedcon = " + usedConnections.size());
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
        ConnectionPoolProperties properties = ConnectionPoolProperties.INSTANCE;
        initConnections = properties.getInitConnections();
        maxConnections = properties.getMaxConnections();
        url = properties.getUrl();
        user = properties.getUser();
        password = properties.getPassword();
    }

}

