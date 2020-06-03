package by.gruca.cafe;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.dao.impl.AccountDAOImpl;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.impl.AccountServiceImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi!");
        AccountServiceImpl accountService = new AccountServiceImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();

        try {
            Account account = accountDAO.read("mikitka").get();
            System.out.println("account = " + account.toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }


    }
}
