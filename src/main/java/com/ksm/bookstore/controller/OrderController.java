package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.OrderService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for managing individual orders within the admin section.
 * Handles order status updates such as cancellation and completion for administrators.
 * Communicates with OrderService to retrieve and update order data.
 */

@Named
@ViewScoped
public class OrderController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrderService orderService; 
    
}