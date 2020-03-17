package DAO.repository;

import DAO.exception.DAOException;
import model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> get(int id) throws DAOException;

    List<Account> getAll() throws DAOException;

    void save(Account t) throws DAOException;

    void update(Account t) throws DAOException;

    void delete(Account t) throws DAOException;
}
