package com.vladkostromin.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T findById(ID id);
    List<T> getAll();
    T save (T t);
    T update(T t);
    T deleteById(ID id);
}
