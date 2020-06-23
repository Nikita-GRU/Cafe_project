package by.gruca.cafe.service;


import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.List;

public interface AccountService {

    void createAccount(Account account) throws ServiceException;

    void updateAccount(Account account, String email, String firstName, String lastName) throws ServiceException;

    boolean changePassword(Account account, String oldPassword, String newPassword) throws ServiceException;

    void setAccountRole(Account account, Role role) throws ServiceException;

    Account getAccountByEmail(String email, String password) throws ServiceException;

    Account getGuestAccountByEmail(String email) throws ServiceException;

    List<Account> getAllAccounts() throws ServiceException;
}
