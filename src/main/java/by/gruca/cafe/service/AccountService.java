package by.gruca.cafe.service;


import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.exception.ServiceException;

public interface AccountService {

    void createAccount(String login, String password) throws ServiceException;



    Account getAccountByLogin(String login) throws ServiceException;


    
}
