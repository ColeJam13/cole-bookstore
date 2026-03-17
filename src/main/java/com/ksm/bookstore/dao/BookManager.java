package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.jpa.Author;

import java.util.List;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Book objects
 * 
 * @author Cole
 */

@Stateless
public class BookManager extends BaseManager<Book> {

    public BookManager() {
        super(Book.class);
    }

    public Book findByTitle(String title) {
        String query = "SELECT b FROM Book b WHERE b.title = :title";
        return entityManager.createQuery(query, Book.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public Book findByIsbn(String isbn) {
        String query = "SELECT i FROM Book i WHERE i.isbn = :isbn";
        return entityManager.createQuery(query, Book.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    public List<Book> findByAuthor(Author author) {
        String query = "SELECT a FROM Book a WHERE a.author = :author";
        return entityManager.createQuery(query, Book.class)
                .setParameter("author", author)
                .getResultList();
    }

}
