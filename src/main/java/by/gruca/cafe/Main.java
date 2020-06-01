package by.gruca.cafe;

import by.gruca.cafe.DAO.exception.DAOException;
import by.gruca.cafe.DAO.repository.impl.AccountDAO;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.impl.AccountServiceImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi!");
        AccountServiceImpl accountService = new AccountServiceImpl();
        AccountDAO accountDAO = new AccountDAO();

        try {
            Account account = accountDAO.read("11").get();
            System.out.println("account = " + account.toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }


    }
}
