package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.BookService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the public home page of the bookstore application.
 * Handles book browsing and search functionality for public users.
 * Communicates with BookService to retrieve and filter book data.
 */

@Named
@ViewScoped
public class HomeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BookService bookService;
    
}
