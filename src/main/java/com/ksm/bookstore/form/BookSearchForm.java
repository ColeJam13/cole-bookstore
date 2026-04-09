package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Book;

import java.util.List;
import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Form that holds the view state data for the home page
 */
@Named
@ViewScoped
@Getter
@Setter
public class BookSearchForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<Book> searchResults;

    private Book selectedBook; 

    @Inject
    private BookManager bookManager;

    /**
     * Method that runs automatically once the page is constructed to
     * get All the books from the database and have them available to search 
     */
    @PostConstruct
    public void init() {
        searchResults = bookManager.findAll();
    }
}
