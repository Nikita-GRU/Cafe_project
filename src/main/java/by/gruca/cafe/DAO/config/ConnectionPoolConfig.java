package by.gruca.cafe.DAO.config;

import java.util.ResourceBundle;

public class ConnectionPoolConfig {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("connectionPool");

    // класс извлекает информацию из файла config.properties
    private ConnectionPoolConfig() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
