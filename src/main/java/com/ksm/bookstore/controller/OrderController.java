package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.service.OrderService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller that interacts with the Order Object
 */

@Named
@ViewScoped
public class OrderController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrderService orderService; 
    
}