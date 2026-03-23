package com.ksm.bookstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.dao.OrderManager;
/**
 * Service class containing business logic for Order related operations.
 * Acts as an intermediary between OrderManager and CustomerManager and the controller layer.
 */

@ApplicationScoped
public class OrderService {
    
    @Inject
    private OrderManager orderManager;

    @Inject
    private CustomerManager customerManager;
}
