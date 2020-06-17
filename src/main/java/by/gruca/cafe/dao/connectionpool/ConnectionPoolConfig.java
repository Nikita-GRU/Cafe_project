package by.gruca.cafe.dao.connectionpool;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectionPoolConfig {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("connectionpool");

    private ConnectionPoolConfig() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
