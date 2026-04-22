package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Author;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * DAO for accessing/updating information on Author objects
 * 
 */

@Stateless
public class AuthorManager extends BaseManager<Author> {

    private static final String QUERY_FIND_BY_NAME = "SELECT a FROM Author a WHERE a.name = :name";

    private static final String QUERY_FIND_ALL_ACTIVE_AUTHORS = "SELECT a FROM Author a WHERE a.active = true";


    public AuthorManager() {
        super(Author.class);
    }
    
    /**
     * Finds a single author by their name
     *
     * @param name the name to search for
     * @return the matching Author entity
     */
    public Author findByName(String name) {
        try {
        return entityManager.createQuery(QUERY_FIND_BY_NAME, Author.class)
                .setParameter("name", name)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Method that finds and returns all authors where the active tag is true
     * @return all active authors
     */
    public List<Author> findAllActiveAuthors() {

        return entityManager.createQuery(QUERY_FIND_ALL_ACTIVE_AUTHORS, Author.class)
                .getResultList();
    }
}
