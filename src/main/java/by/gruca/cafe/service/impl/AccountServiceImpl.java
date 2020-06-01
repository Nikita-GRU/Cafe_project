package by.gruca.cafe.service.impl;


import by.gruca.cafe.DAO.exception.DAOException;
import by.gruca.cafe.DAO.repository.impl.AccountDAO;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;

public class AccountServiceImpl implements AccountService {
    @Override
    public void createAccount(String login, String password) throws ServiceException {
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        AccountDAO accountDAO = new AccountDAO();
        try {
            accountDAO.create(account);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public Account getAccountByLogin(String login) throws ServiceException {
        Account account = null;
        AccountDAO accountDAO = new AccountDAO();
        try {
            account = accountDAO.read(login).get();
        } catch (DAOException e) {
            throw new ServiceException("get acc by login service exception", e);
        }
        return account;
    }
}
