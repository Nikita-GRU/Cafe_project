package by.gruca.cafe.configuration;

import java.util.ResourceBundle;

public class UrlManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("urlconfig");

    private UrlManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
