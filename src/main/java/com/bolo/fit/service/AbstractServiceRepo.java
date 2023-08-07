package com.bolo.fit.service;

import com.bolo.fit.repository.GenericRepository;

import java.io.Serializable;

public abstract class AbstractServiceRepo<R extends GenericRepository<T, I>, T, I extends Serializable> extends AbstractService {
    protected R repository;

    public AbstractServiceRepo(R repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        return repository.save(entity);
    }
}
