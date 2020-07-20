package by.gruca.cafe.dao;


import by.gruca.cafe.dao.exception.DAOException;

import java.util.Optional;

public interface AbstractDAO<Entity, Key> {
    int create(Entity model) throws DAOException, DAOException;

    Optional<Entity> read(Key key) throws DAOException;

    int update(Entity model) throws DAOException;

    boolean delete(Entity model) throws DAOException;
}
