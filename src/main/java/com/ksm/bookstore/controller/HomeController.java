package com.ksm.bookstore.controller;

import java.io.Serializable;
import java.io.IOException;

import javax.faces.context.FacesContext;

import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.form.BookSearchForm;
import com.ksm.bookstore.jpa.Book;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the public home page of the bookstore application.
 * Handles book browsing and search functionality for public users.
 * Communicates with BookService to retrieve and filter book data.
 */

@Named
@RequestScoped
@Getter
@Setter
public class HomeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BookManager bookManager;

    @Inject
    private BookSearchForm bookSearchForm;

    private Book selectedBook;

    /**
     * Method that runs automatically once the page is constructed to
     * get All the books from the database and have them available to search 
     */
    @PostConstruct
    public void init() {
        bookSearchForm.setSearchResults(bookManager.findAll());
    }

    /**
     * Method that navigates to the book detail pages via ISBN when 
     * a user clicks that books row
     * @throws IOException
     */
    public void navigate() throws IOException {
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .redirect("book-detail.jsf?isbn=" + selectedBook.getIsbn());
    }

}
