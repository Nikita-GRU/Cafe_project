package by.gruca.cafe.dao;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDAO extends AbstractDAO<Account> {
    Optional<Account> read(String email) throws DAOException;

    List<Account> readAll(int itemsPerPage, int pageNumber) throws DAOException;

    int readAccountCount() throws DAOException;
}
