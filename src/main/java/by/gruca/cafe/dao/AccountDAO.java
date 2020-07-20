package by.gruca.cafe.dao;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Account;

import java.util.List;

public interface AccountDAO extends AbstractDAO<Account,String>{

    List<Account> getAllAccounts() throws DAOException;

    double getAccountBalance(String email) throws DAOException;

    void setAccountBonus(String email, int bonus) throws DAOException;

    void setAccountBalance(String email, double balance) throws DAOException;

    int getAccountBonus(String email) throws DAOException;
}
