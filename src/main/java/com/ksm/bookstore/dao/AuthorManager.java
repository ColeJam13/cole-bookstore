package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Author;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Author objects
 * 
 * @author Cole
 */

@Stateless
public class AuthorManager extends BaseManager<Author> {

    public AuthorManager() {
        super(Author.class);
    }
    
    // SELECT (nickname) FROM Author (class) (nickname) WHERE (nickname).(name field on Author) = :blank
    public Author findByName(String name) {
        String query = "SELECT n FROM Author n WHERE n.name = :name";
        return entityManager.createQuery(query, Author.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
