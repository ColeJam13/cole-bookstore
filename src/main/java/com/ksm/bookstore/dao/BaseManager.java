package com.ksm.bookstore.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Base abstract DAO that sets up the framework for the rest of the managers
 * to recieve and send information from the database
 * 
 */

public abstract class BaseManager<T> {

    /** 
     * The JPA EntityManager, injected by CDI, used to interact with Database
     * 
     */
    @Inject
    protected EntityManager entityManager;

    private Class<T> entityClass;

    /**
     * Constructs a BaseManager for the given entity class
     *
     * @param entityClass the class of the entity this manager handles
     */
    protected BaseManager(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Finds a single entity by its primary key ID
     *
     * @param id the primary key to search for
     * @return the matching entity, or null if not found
     */
    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    /**
     * Returns all entities of this type from the database
     *
     * @return a list of all entities
     */
    public List<T> findAll() {
        String query = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(query, entityClass).getResultList();
    }
    
    /**
     * Persists a new entity to the database
     *
     * @param entity the entity to create
     */
    public void create(T entity) {
        entityManager.persist(entity);
    }

    /**
     * Updates an existing entity in the database
     *
     * @param entity the entity to update
     */
    public void update(T entity) {
        entityManager.merge(entity);
    }

    /**
     * Removes an entity from the database
     *
     * @param entity the entity to delete
     */
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
