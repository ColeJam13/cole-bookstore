package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.OrderService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the admin dashboard page of the bookstore application.
 * Handles display and filtering of submitted orders for administrators.
 * Communicates with OrderService to retrieve and manage order data.
 */

@Named
@ViewScoped
public class DashboardController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrderService orderService;
    
}