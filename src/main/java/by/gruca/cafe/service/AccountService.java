package by.gruca.cafe.service;


import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.List;

public interface AccountService {

    void createAccount(Account account) throws ServiceException;

    void updateAccount(Account newAccount, String email) throws ServiceException;

    boolean changePassword(Account account, String oldPassword, String newPassword) throws ServiceException;

    void setAccountRole(Account account, Role role) throws ServiceException;

    Account getAccountByEmailAndPassword(String email, String password) throws ServiceException;

    Account getAccountByEmail(String email) throws ServiceException;

    List<Account> getAllAccounts() throws ServiceException;


}
