package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    private static ApplicationProperties INSTANCE = new ApplicationProperties();
    private String url;
    private String user;
    private String password;

    private ApplicationProperties() {
        setProperties();
    }

    public static ApplicationProperties getInstance() {
        return INSTANCE;
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

    private void setProperties() {
        try {
            InputStream in = new FileInputStream("./src/main/resources/application.properties");
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("dbUrl");
            user = properties.getProperty("dbUser");
            password = properties.getProperty("dbPassword");

        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
