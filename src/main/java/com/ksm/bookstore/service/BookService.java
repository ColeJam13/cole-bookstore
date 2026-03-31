package com.ksm.bookstore.service;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;

/**
 * Service class containing business logic for Book related operations.
 * Acts as an intermediary between BookManager and AuthorManager and the controller layer.
 */

@ApplicationScoped
public class BookService {

    @Inject
    private BookManager bookManager;

    @Inject 
    private AuthorManager authorManager;
   
    /**
     * Method that returns a list of books written by the searched Author
     * @param author
     * @return list of books written by that specified author
     */
    public List<Book> getBookByAuthor(String author) {
        Author foundAuthor = authorManager.findByName(author);

        if (foundAuthor == null) {
            return Collections.emptyList();
            }
        return bookManager.findByAuthor(foundAuthor);
    }
}
