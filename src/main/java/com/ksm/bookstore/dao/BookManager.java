package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.jpa.Author;

import java.util.List;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Book objects
 * 
 */

@Stateless
public class BookManager extends BaseManager<Book> {

    private static final String QUERY_FIND_BY_TITLE = "SELECT t FROM Book t WHERE t.title = :title";

    private static final String QUERY_FIND_BY_ISBN = "SELECT i FROM Book i WHERE i.isbn = :isbn";

    private static final String QUERY_FIND_BY_AUTHOR = "SELECT a FROM Book a WHERE a.author = :author";


    public BookManager() {
        super(Book.class);
    }

    public Book findByTitle(String title) {
        return entityManager.createQuery(QUERY_FIND_BY_TITLE, Book.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public Book findByIsbn(String isbn) {
        return entityManager.createQuery(QUERY_FIND_BY_ISBN, Book.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    public List<Book> findByAuthor(Author author) {
        return entityManager.createQuery(QUERY_FIND_BY_AUTHOR, Book.class)
                .setParameter("author", author)
                .getResultList();
    }

}
