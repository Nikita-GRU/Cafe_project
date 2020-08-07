package by.gruca.cafe.service;


import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.PaymentType;
import by.gruca.cafe.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void changePassword(String emailParam, String oldPasswordParam, String newPasswordParam) throws ServiceException;

    Account getAccountByEmailAndPassword(String emailParam, String passwordParam) throws ServiceException;

    Account getAccountByEmail(String emailParam) throws ServiceException;

    List<Account> getAllAccounts() throws ServiceException;

    void createAccount(String emailParam, String passwordParam, String phoneNumberParam, String firstNameParam, String roleParam) throws ServiceException;

    void createGuestAccount(String emailParam, String phoneNumberParam, String firstNameParam) throws ServiceException;

    void updateAccount(String emailParam, String roleParam, String bonusParam) throws ServiceException;

    void updateAccountBalanceAndBonus(Account account, BigDecimal price, int bonusToPay, PaymentType paymentType) throws ServiceException;

    List<Account> getPaginatedAccounts(int itemsPerPage, int pageNumber) throws ServiceException;

    int getAccountsCount() throws ServiceException;
}
