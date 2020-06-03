package by.gruca.cafe.service.impl;


import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.dao.impl.AccountDAOImpl;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;

public class AccountServiceImpl implements AccountService {
    @Override
    public void createAccount(String login, String password) throws ServiceException {
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        try {
            accountDAO.create(account);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public Account getAccountByLogin(String login) throws ServiceException {
        Account account = null;
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        try {
            account = accountDAO.read(login).get();
        } catch (DAOException e) {
            throw new ServiceException("get acc by login service exception", e);
        }
        return account;
    }
}
