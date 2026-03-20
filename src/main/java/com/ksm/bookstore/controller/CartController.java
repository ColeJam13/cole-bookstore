package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.BookService;
import com.ksm.bookstore.service.OrderService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller that interacts with the Cart page
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
