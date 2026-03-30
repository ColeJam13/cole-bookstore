package com.ksm.bookstore.controller;

import java.io.Serializable;
import java.io.IOException;

import org.omnifaces.cdi.ViewScoped;
import javax.faces.context.FacesContext;

import com.ksm.bookstore.service.BookService;
import com.ksm.bookstore.jpa.Book;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

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

    private List<Book> searchResults;

    private Book selectedBook;

    /**
     * Method that runs automatically once the page is constructed to
     * get All the books from the database and have them available to search 
     */
    @PostConstruct
    public void init() {
        searchResults = bookService.getAllBooks();
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
