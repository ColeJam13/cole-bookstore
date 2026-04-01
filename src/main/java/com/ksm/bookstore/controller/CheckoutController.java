package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.CustomerService;
import com.ksm.bookstore.service.OrderService;

import javax.inject.Inject;
import javax.inject.Named;
/**
 * Controller for the checkout page of the bookstore application.
 * Handles order submission and customer identification for public users.
 * Communicates with OrderService and CustomerService to process purchases.
 */

@Named
@ViewScoped // CHANGE TO REQUEST SCOPED AND MAKE FORM
public class CheckoutController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private OrderService orderService;

    @Inject
    private CustomerService customerService;
    
}