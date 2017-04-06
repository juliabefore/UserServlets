package ua.lazareva.userregister.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T, K extends Serializable> {
    List<T> getAll();

    T getById(K objId);

    void add(T obj);

    void edit(T obj);

    void delete(K objId);
}
