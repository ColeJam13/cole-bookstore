package com.ksm.bookstore.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;

/**
 * Service class containing business logic for Author related operations.
 * Acts as an intermediary between AuthorManager and the controller layer.
 */

@Stateless
public class AuthorService {

    @Inject
    private AuthorManager authorManager;

    @Inject
    private BookManager bookManager;

    /**
     * Finds the list of books by a given deactivated author and sets their 
     * active flag to false
     * @param author the author to be deactivated
     */
    public void deactivateAuthor(Author author) {
        author.setActive(false);
        authorManager.update(author);
        List<Book> booksByAuthor = bookManager.findByAuthor(author);
            for (Book book : booksByAuthor) {
                book.setActive(false);
                bookManager.update(book);
            }
    }
    
}
