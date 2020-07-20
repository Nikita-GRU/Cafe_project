package by.gruca.cafe.service.impl;


import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.HashGeneratorUtil;
import by.gruca.cafe.util.UtilException;
import by.gruca.cafe.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;


public class AccountServiceImpl implements AccountService {
    HashGeneratorUtil hashGeneratorUtil;
    Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl() {
        hashGeneratorUtil = new HashGeneratorUtil();
    }


    @Override
    public List<Account> getAllAccounts() throws ServiceException {
        List<Account> accounts;
        try {
            accounts = DAOFactory.INSTANCE.getAccountDAO().getAllAccounts();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return accounts;
    }

    @Override
    public void updateAccount(Account newAccount, String email) throws ServiceException {
        Account account = null;
        try {
            account = ServiceFactory.INSTANCE.getAccountService().getAccountByEmail(email);
            if (newAccount.getRole() != null) {
                account.setRole(newAccount.getRole());
            }
            if (newAccount.getFirstName() != null) {
                account.setFirstName(newAccount.getFirstName());
            }
            if (newAccount.getPhoneNumber() != 0) {
                account.setPhoneNumber(newAccount.getPhoneNumber());
            }
            if (newAccount.getBonusPoints() != 0) {
                account.setBonusPoints(newAccount.getBonusPoints());
            }
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
    public void setAccountRole(Account account, Role role) throws ServiceException {
        account.setRole(role);
        try {
            DAOFactory.INSTANCE.getAccountDAO().update(account);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createAccount(Account account) throws ServiceException {

        try {
            account.setPassword(hashGeneratorUtil.generateHash(account.getPassword()));
            if (account.getRole() == null) {
                account.setRole(Role.USER);
            }
            DAOFactory.INSTANCE.getAccountDAO().create(account);
            logger.info("Account" + account.getEmail() + " created");
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Account getAccountByEmailAndPassword(String email, String password) throws ServiceException {
        Account account = null;
        try {
            account = DAOFactory.INSTANCE.getAccountDAO().read(email).get();
            if (!(hashGeneratorUtil.validatePassword(password, account.getPassword()))) {
                throw new UtilException("Invalid password");
            }
            logger.info("Account" + email + " is taken");
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return account;
    }

    @Override
    public Account getAccountByEmail(String email) throws ServiceException {
        Account account = null;
        try {
            account = DAOFactory.INSTANCE.getAccountDAO().read(email).get();
            logger.info("Account" + email + " is taken");
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return account;
    }

    @Override
    public void createAccount(String emailParam, String passwordParam, String phoneNumberParam, String firstNameParam) throws ServiceException {

        try {
            if (Validator.validateEmail(emailParam) && Validator.validateLong(phoneNumberParam)//do it in service
                    && Validator.validateNames(firstNameParam) && Validator.validatePassword(passwordParam)) {
                Account account = buildAccount(emailParam, phoneNumberParam, firstNameParam);
                account.setPassword(hashGeneratorUtil.generateHash(passwordParam));
                account.setRole(Role.USER);
                Optional<Account> possibleExistingAccount = DAOFactory.INSTANCE.getAccountDAO().read(emailParam);
                if (possibleExistingAccount.isPresent() && possibleExistingAccount.get().getRole() == Role.GUEST) {
                    DAOFactory.INSTANCE.getAccountDAO().update(account);
                } else {
                    DAOFactory.INSTANCE.getAccountDAO().create(account);
                }
                logger.info("Account" + account.getEmail() + " created");
            } else {
                throw new ServiceException("Invalid input data");
            }
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createGuestAccount(String emailParam, String phoneNumberParam, String firstNameParam) throws ServiceException {
        if (Validator.validateEmail(emailParam) && Validator.validateLong(phoneNumberParam)
                && Validator.validateNames(firstNameParam)) {
            try {
                Account account = buildAccount(emailParam, phoneNumberParam, firstNameParam);
                account.setPassword(hashGeneratorUtil.generateHash(String.valueOf((Math.random() * 2048))));
                account.setRole(Role.GUEST);
                DAOFactory.INSTANCE.getAccountDAO().create(account);
                logger.info("Account" + account.getEmail() + " created");

            } catch (DAOException | UtilException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("Invalid input data");
        }
    }

    @Override
    public double getBalance(String emailParam) throws ServiceException {
        double balance;
        try {
            balance = DAOFactory.INSTANCE.getAccountDAO().getAccountBalance(emailParam);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return balance;
    }

    private Account buildAccount(String email, String phoneNumber, String firstName) {
        Account account = new Account();
        account.setEmail(email);
        account.setPhoneNumber(Long.parseLong(phoneNumber));
        account.setFirstName(firstName);
        return account;
    }
}
