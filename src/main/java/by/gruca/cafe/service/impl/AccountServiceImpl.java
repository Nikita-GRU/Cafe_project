package by.gruca.cafe.service.impl;


import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.PaymentType;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.HashGenerator;
import by.gruca.cafe.util.RequestParameterConverter;
import by.gruca.cafe.util.UtilException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public class AccountServiceImpl implements AccountService {
    HashGenerator hashGenerator;
    Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl() {
        hashGenerator = new HashGenerator();
    }


    @Override
    public List<Account> getAllAccounts() throws ServiceException {
        List<Account> accounts ;

        try {
            accounts = DAOFactory.INSTANCE.getAccountDAO().readAll();

        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return accounts;
    }

    @Override
    public void updateAccount(String emailParam, String roleParam, String bonusParam) throws ServiceException {
        Account account;
        try {
            Role role = RequestParameterConverter.INSTANCE.valueOfRole(roleParam);
            int bonus = RequestParameterConverter.INSTANCE.valueOfInteger(bonusParam);

            account = DAOFactory.INSTANCE.getAccountDAO().read(emailParam)
                    .orElseThrow(() -> new ServiceException("Account not found"));
            account.setBonus(bonus);
            account.setRole(role);

            DAOFactory.INSTANCE.getAccountDAO().update(account);

        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changePassword(String emailParam, String oldPasswordParam, String newPasswordParam) throws ServiceException {
//        try {
//            Account account = DAOFactory.INSTANCE.getAccountDAO().read(emailParam)
//                    .orElseThrow(() -> new ServiceException("Account not found"));
//
//            if (hashGeneratorUtil.validatePassword(account.getPassword(), oldPasswordParam)) {
//                account.setPassword(hashGeneratorUtil.generateHash(newPasswordParam));
//                DAOFactory.INSTANCE.getAccountDAO().update(account);
//            }
//        } catch (UtilException | DAOException e) {
//            logger.error(e);
//            throw new ServiceException(e);
//        }
    }

    @Override
    public List<Account> getPaginatedAccounts(int itemsPerPage, int pageNumber) throws ServiceException {
       List<Account> accounts;
        try {
            accounts = DAOFactory.INSTANCE.getAccountDAO().readAll(itemsPerPage, pageNumber);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return accounts;
    }

    @Override
    public int getAccountsCount() throws ServiceException {
        int accountsCount;
        try {
            accountsCount = DAOFactory.INSTANCE.getAccountDAO().readAccountCount();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return accountsCount;
    }

    @Override
    public Account getAccountByEmailAndPassword(String emailParam, String passwordParam) throws ServiceException {
        Account account = null;
        try {
            account = DAOFactory.INSTANCE.getAccountDAO().read(emailParam)
                    .orElseThrow(() -> new ServiceException("Account not found"));
            if (!(hashGenerator.validatePassword(passwordParam, account.getPassword()))) {
                throw new UtilException("Invalid password");
            }
            logger.info("Account" + emailParam + " is taken");
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return account;
    }

    @Override
    public Account getAccountByEmail(String emailParam) throws ServiceException {
        Account account = null;
        try {
            account = DAOFactory.INSTANCE.getAccountDAO().read(emailParam)
                    .orElse(new Account());
            logger.info("Account" + emailParam + " is taken");
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return account;
    }

    @Override
    public void updateAccountBalanceAndBonus(Account account, BigDecimal price,
                                             int bonusToPay, PaymentType paymentType) throws ServiceException {
        try {
            if(bonusToPay>0){
                account.setBonus(account.getBonus() - bonusToPay);
            }
            if(paymentType.equals(PaymentType.BALANCE)){
                account.setBalance(account.getBalance().subtract(price));
            }

            DAOFactory.INSTANCE.getAccountDAO().update(account);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createAccount(String emailParam, String passwordParam, String phoneNumberParam, String
            firstNameParam, String roleParam) throws ServiceException {

        try {
//            if (Validator.validateEmail(emailParam)
//                    && Validator.validateNames(firstNameParam) && Validator.validatePassword(passwordParam)) {
            Account account = buildAccount(emailParam, phoneNumberParam, firstNameParam);
            account.setPassword(hashGenerator.generateHash(passwordParam));
            account.setRole(Role.USER);
            Optional<Account> possibleExistingAccount = DAOFactory.INSTANCE.getAccountDAO().read(emailParam);
            if (possibleExistingAccount.isPresent() && possibleExistingAccount.get().getRole() == Role.GUEST) {
                DAOFactory.INSTANCE.getAccountDAO().update(account);
            } else {
                DAOFactory.INSTANCE.getAccountDAO().create(account);
            }
            logger.info("Account" + account.getEmail() + " created");
//            } else {
//                throw new ServiceException("Invalid input data");
//            }
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createGuestAccount(String emailParam, String phoneNumberParam, String firstNameParam) throws
            ServiceException {
//        if (Validator.validateEmail(emailParam) && Validator.validateLong(phoneNumberParam)
//                && Validator.validateNames(firstNameParam)) {
        try {
            Account account = buildAccount(emailParam, phoneNumberParam, firstNameParam);
            account.setPassword(hashGenerator.generateHash(String.valueOf((Math.random() * 2048))));
            account.setRole(Role.GUEST);
            DAOFactory.INSTANCE.getAccountDAO().create(account);

        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
//        } else {
//            throw new ServiceException("Invalid input data");
//        }
    }


    private Account buildAccount(String emailParam, String phoneNumberParam, String firstNameParam) {
        Account account = new Account();
        account.setEmail(emailParam);
        account.setPhoneNumber(phoneNumberParam);
        account.setFirstName(firstNameParam);
        account.setBonus(0);
        account.setBalance(BigDecimal.valueOf(0.0));
        return account;
    }

}
