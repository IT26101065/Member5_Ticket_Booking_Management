package com.cinereserve.ticket.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    boolean save(T entity);
    boolean update(T entity);
    boolean deleteById(ID id);
    boolean existsById(ID id);
}