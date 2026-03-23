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

    private static final String QUERY_FIND_BY_TITLE = "SELECT b FROM Book b WHERE b.title = :title";

    private static final String QUERY_FIND_BY_ISBN = "SELECT b FROM Book b WHERE b.isbn = :isbn";

    private static final String QUERY_FIND_BY_AUTHOR = "SELECT b FROM Book b WHERE b.author = :author";


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
        return entityManager.createQuery(QUERY_FIND_BY_TITLE, Book.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    /**
     * Finds a single book by its ISBN
     *
     * @param isbn the ISBN to search for
     * @return the matching Book entity
     */
    public Book findByIsbn(String isbn) {
        return entityManager.createQuery(QUERY_FIND_BY_ISBN, Book.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
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

}
