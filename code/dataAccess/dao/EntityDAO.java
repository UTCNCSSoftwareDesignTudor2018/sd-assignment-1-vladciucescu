package dataAccess.dao;

import dataAccess.entity.DataEntity;

import java.util.List;

public interface EntityDAO<T extends DataEntity> {

    T find(int id) throws DataAccessException;

    List<T> findAll() throws DataAccessException;

    int insert(T obj) throws DataAccessException;

    void update(T obj) throws DataAccessException;

    void delete(int id) throws DataAccessException;
}
