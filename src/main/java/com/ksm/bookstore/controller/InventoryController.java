package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.AuthorService;
import com.ksm.bookstore.service.BookService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller that interacts with the Admin Inventory page
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