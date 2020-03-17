package DAO.config;

import DAO.db.SQLConnectionPool;
import DAO.exception.PropertiesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ConnectionPoolProperties {
    INSTANCE;

    Logger logger = LogManager.getLogger(SQLConnectionPool.class);
    private String url;
    private String user;
    private String password;
    private int initConnections;
    private int maxConnections;
    private int increaseStep;


    ConnectionPoolProperties() {
        try {
            setProperties();
        } catch (PropertiesException e) {
            logger.error("error loading connection pool properties");
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getInitConnections() {
        return initConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public int getIncreaseStep() {
        return increaseStep;
    }

    private void setProperties() throws PropertiesException {
        try {
            InputStream in = new FileInputStream("./src/main/resources/connectionPool.properties");
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("db_url");
            user = properties.getProperty("db_user");
            password = properties.getProperty("db_password");
            initConnections = Integer.parseInt(properties.getProperty("init_size"));
            maxConnections = Integer.parseInt(properties.getProperty("max_size"));
            increaseStep = Integer.parseInt(properties.getProperty("increase_step"));
        } catch (IOException | NumberFormatException e) {
            throw new PropertiesException("Setting properties error", e);
        }
    }

    @Override
    public String toString() {
        return "ConnectionPoolProperties{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", initConnections=" + initConnections +
                ", maxConnections=" + maxConnections +
                ", increaseStep=" + increaseStep +
                '}';
    }
}
