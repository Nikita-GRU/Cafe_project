package by.gruca.cafe.service.impl;


import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.dao.impl.AccountDAOImpl;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.HashGeneratorUtil;
import by.gruca.cafe.util.UtilException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AccountServiceImpl implements AccountService {
    HashGeneratorUtil hashGeneratorUtil;
    Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl() {
        hashGeneratorUtil = new HashGeneratorUtil();
    }

    @Override
    public void updateAccount(Account account, String email, String firstName, String lastName) throws ServiceException {
        account.setEmail(email);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        try {
            DAOFactory.INSTANCE.getAccountDAO().update(account);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changePassword(Account account, String oldPassword, String newPassword) throws ServiceException {
        try {
            if (hashGeneratorUtil.validatePassword(account.getPassword(), oldPassword)) {
                account.setPassword(hashGeneratorUtil.generateHash(newPassword));
                DAOFactory.INSTANCE.getAccountDAO().update(account);
                return true;
            }
        } catch (UtilException | DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public void setBanStatus(Account account, boolean banStatus) throws ServiceException {
        account.setEnabled(false);
        try {
            DAOFactory.INSTANCE.getAccountDAO().update(account);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createAccount(String login, String password, String email, String firstName, String lastName) throws ServiceException {

        AccountDAOImpl accountDAO = new AccountDAOImpl();
        Account account = new Account();
        account.setLogin(login);
        account.setEmail(email);
        try {
            account.setPassword(hashGeneratorUtil.generateHash(password));
            accountDAO.create(account);
            logger.info("Account" + login + " created");
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Account getAccountByLogin(String login) throws ServiceException {
        Account account = null;
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        try {
            account = accountDAO.read(login).get();
            logger.info("Account" + login + " is taken");
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return account;
    }
}
