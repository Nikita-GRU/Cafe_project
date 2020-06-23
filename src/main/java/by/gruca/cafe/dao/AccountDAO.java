package by.gruca.cafe.dao;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Account;

import java.util.List;

public interface AccountDAO extends AbstractDAO<Account,String>{

    List<Account> getAllAccounts() throws DAOException;
}
