package by.gruca.cafe.util;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.dao.impl.AccountDAOImpl;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.AccountServiceImpl;

public class Main {
    public static void main(String[] args) {
        AccountServiceImpl accountDAO = new AccountServiceImpl();
        try {
            Account account = accountDAO.getAccountByLogin("Nikita-GRU");
            System.out.println(account.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
