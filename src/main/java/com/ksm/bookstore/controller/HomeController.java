package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.BookService;
import com.ksm.bookstore.jpa.Book;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Collections;

/**
 * Controller for the public home page of the bookstore application.
 * Handles book browsing and search functionality for public users.
 * Communicates with BookService to retrieve and filter book data.
 */

@Named
@ViewScoped
@Getter
@Setter
public class HomeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BookService bookService;

    private String searchTerm;

    private String searchType;

    private String sortField;

    private List<Book> searchResults;
    

    /**
     * Method that runs automatically once the page is contructed to
     * get All the books from the database and have them available to search 
     */
    @PostConstruct
    public void init() {
        searchResults = bookService.getAllBooks();
    }

    /**
     * Method that runs when a user searches for a Book object(s). Depending on
     * user input, will select the matching field to search by
     */
    public void search() {
        if (searchType.equals("title")) {
            searchResults = Collections.singletonList(bookService.getBookByTitle(searchTerm));
        } else if (searchType.equals("author")) {
            searchResults = bookService.getBookByAuthor(searchTerm);
        } else if (searchType.equals("isbn")) {
            searchResults = Collections.singletonList(bookService.getBookByIsbn(searchTerm));
        }
    }
}
