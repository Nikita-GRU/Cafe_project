package by.gruca.cafe.dao.connectionpool;

public class PropertiesException extends Exception{
    public PropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesException(Throwable cause) {
        super(cause);
    }
}
