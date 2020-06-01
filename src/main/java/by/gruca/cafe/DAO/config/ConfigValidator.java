package by.gruca.cafe.DAO.config;

public enum ConfigValidator {
    INSTANCE;
    private final ConnectionPoolProperties properties;

    ConfigValidator() {
        properties = ConnectionPoolProperties.INSTANCE;
    }

    public boolean validateProperties() {
        return true;
    }
}
