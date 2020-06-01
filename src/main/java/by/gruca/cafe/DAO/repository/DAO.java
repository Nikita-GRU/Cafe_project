package by.gruca.cafe.DAO.repository;


import by.gruca.cafe.DAO.exception.DAOException;

import java.util.Optional;

public interface DAO<Entity, Key> {
    boolean create(Entity model) throws DAOException, DAOException;

    Optional<Entity> read(Key key) throws DAOException;

    int update(Entity model) throws DAOException;

    boolean delete(Entity model) throws DAOException;
}
