package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Author;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Author objects
 * 
 */

@Stateless
public class AuthorManager extends BaseManager<Author> {

    private static final String QUERY_FIND_BY_NAME = "SELECT n FROM Author n WHERE n.name = :name";

    public AuthorManager() {
        super(Author.class);
    }
    
    // SELECT (nickname) FROM Author (class) (nickname) WHERE (nickname).(name field on Author) = :blank
    public Author findByName(String name) {
        return entityManager.createQuery(QUERY_FIND_BY_NAME, Author.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
