package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.BookService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the book detail page of the bookstore application.
 * Handles display of individual book information for public users.
 * Communicates with BookService to retrieve book data.
 */

@Named
@ViewScoped
public class BookController implements Serializable {

    @Inject
    private BookService bookService;

    private static final long serialVersionUID = 1L;
    
}
