package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.BookService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller that interacts with the Home page of the application
 */

@Named
@ViewScoped
public class HomeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BookService bookService;
    
}
