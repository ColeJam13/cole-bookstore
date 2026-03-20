package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.BookService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller that interacts with the Book object
 */

@Named
@ViewScoped
public class BookController implements Serializable {

    @Inject
    private BookService bookService;

    private static final long serialVersionUID = 1L;
    
}
