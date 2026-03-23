package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.BookService;
import com.ksm.bookstore.service.OrderService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the shopping cart page of the bookstore application.
 * Handles adding, removing, and displaying books in the users cart.
 * Communicates with BookService and OrderService to manage cart contents.
 */

@Named
@ViewScoped
public class CartController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BookService bookService;

    @Inject
    private OrderService orderService;
    
}
