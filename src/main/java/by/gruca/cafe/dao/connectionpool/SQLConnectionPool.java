package by.gruca.cafe.dao.connectionpool;


import by.gruca.cafe.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public enum SQLConnectionPool {
    INSTANCE;
    AtomicBoolean isActive;
    LinkedBlockingQueue<ConnectionProxy> connectionPool;
    List<ConnectionProxy> usedConnections;
    private Logger logger = LogManager.getLogger(SQLConnectionPool.class);
    private int initConnections;
    private int maxConnections;
    private String url;
    private String user;
    private String password;

    SQLConnectionPool() {
        isActive = new AtomicBoolean();
        isActive.getAndSet(false);
        connectionPool = new LinkedBlockingQueue<>();
        usedConnections = new ArrayList<>();
    }

    private ConnectionProxy createConnection() throws DAOException {
        ConnectionProxy connection;
        try {
            connection = new ConnectionProxy(DriverManager.getConnection(url, user, password));
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return connection;
    }

    private void createConnectionPool() throws DAOException {
        isActive.getAndSet(true);
        initProperties();
        for (int i = 0; i < initConnections; i++) {
            try {
                connectionPool.put(createConnection());
            } catch (InterruptedException e) {
                logger.error(e);
                Thread.currentThread().interrupt();
                throw new DAOException(e);
            }
        }

    }

    public ConnectionProxy getConnection() throws DAOException {
        if (!isActive.get()) {
            createConnectionPool();
        }
        if (connectionPool.isEmpty()) {
            addConnection();
        }
        ConnectionProxy connection = connectionPool.poll();
        usedConnections.add(connection);
        return connection;
    }

    public void releaseConnection(ConnectionProxy connection) {
        usedConnections.remove(connection);
        connectionPool.add(connection);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    private LinkedBlockingQueue<ConnectionProxy> getConnectionPoolConnections() {
        return connectionPool;
    }


    public void shutdown() throws DAOException {

        try {
            for (ConnectionProxy usedConnection : usedConnections) {
                usedConnection.getConnection().close();
            }
            for (int i = 0; i < connectionPool.size(); i++) {
                connectionPool.peek().getConnection().close();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        usedConnections.clear();
        connectionPool.clear();
    }

    private void addConnection() throws DAOException {
        if (usedConnections.size() <= maxConnections) {
            connectionPool.add(createConnection());
        } else {
            throw new DAOException("Too many connections");
        }
    }

    public void initProperties() {
        initConnections = Integer.parseInt(ConnectionPoolConfig.getProperty("init_size"));
        maxConnections = Integer.parseInt(ConnectionPoolConfig.getProperty("max_size"));
        url = ConnectionPoolConfig.getProperty("db_url");
        user = ConnectionPoolConfig.getProperty("db_user");
        password = ConnectionPoolConfig.getProperty("db_password");
    }

}

