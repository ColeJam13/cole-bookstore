package com.ksm.bookstore.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;

/**
 * Base abstract DAO that sets up the framework for the rest of the managers
 * to recieve and send information from the database
 * 
 * @author Cole
 */

@Stateless
public abstract class BaseManager<T> {

    @Inject
    private EntityManager entityManager;

    private Class<T> entityClass;

    protected BaseManager(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        String query = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(query, entityClass).getResultList();
    }
    
    public void create(T entity) {
        entityManager.persist(entity);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
