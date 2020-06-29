package by.gruca.cafe.util;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(Arrays.toString(DAOFactory.INSTANCE.getOrderDAO().getAll().toArray()));
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }
}
