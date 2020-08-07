package by.gruca.cafe.dao;


import by.gruca.cafe.dao.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface AbstractDAO<Entity> {

    long create(Entity entity) throws DAOException;

    Optional<Entity> read(long id) throws DAOException;

    int update(Entity entity) throws DAOException;

    boolean delete(Entity entity) throws DAOException;

    List<Entity> readAll() throws DAOException;
}
