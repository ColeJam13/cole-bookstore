package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.AuthorService;
import com.ksm.bookstore.service.BookService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the admin inventory management page of the bookstore application.
 * Handles adding, editing, and deleting books and authors for administrators.
 * Communicates with BookService and AuthorService to manage inventory data.
 */

@Named
@ViewScoped
public class InventoryController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BookService bookService;

    @Inject
    private AuthorService authorService;
    
}