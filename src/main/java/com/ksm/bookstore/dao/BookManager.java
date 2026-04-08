package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.jpa.Author;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * DAO for accessing/updating information on Book objects
 * 
 */

@Stateless
public class BookManager extends BaseManager<Book> {

    private static final String QUERY_FIND_BY_TITLE = "SELECT b FROM Book b WHERE b.title = :title";

    private static final String QUERY_FIND_BY_ISBN = "SELECT b FROM Book b WHERE b.isbn = :isbn";

    private static final String QUERY_FIND_BY_AUTHOR = "SELECT b FROM Book b WHERE b.author = :author";

    private static final String QUERY_FIND_ALL_ACTIVE_BOOKS = "SELECT b FROM Book b WHERE b.active = true";

    public BookManager() {
        super(Book.class);
    }

    /**
     * Finds a single book by its title
     *
     * @param title the title to search for
     * @return the matching Book entity
     */
    public Book findByTitle(String title) {
        try {
        return entityManager.createQuery(QUERY_FIND_BY_TITLE, Book.class)
                .setParameter("title", title)
                .getSingleResult();
        } catch (NoResultException e) {
        return null;
        }
    }

    /**
     * Finds a single book by its ISBN
     *
     * @param isbn the ISBN to search for
     * @return the matching Book entity
     */
    public Book findByIsbn(String isbn) {
        try {
        return entityManager.createQuery(QUERY_FIND_BY_ISBN, Book.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Finds all books by a given author
     *
     * @param author the Author to search by
     * @return a list of books by the given author
     */
    public List<Book> findByAuthor(Author author) {

        return entityManager.createQuery(QUERY_FIND_BY_AUTHOR, Book.class)
                .setParameter("author", author)
                .getResultList();
    }

    /**
     * Method that finds and returns all books where the active tag is true
     * @return all active books
     */
    public List<Book> findAllActiveBooks() {

        return entityManager.createQuery(QUERY_FIND_ALL_ACTIVE_BOOKS, Book.class)
                .getResultList();
    }

}
