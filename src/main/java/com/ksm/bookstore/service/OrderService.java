package com.ksm.bookstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.dao.OrderManager;

@ApplicationScoped
public class OrderService {
    
    @Inject
    private OrderManager orderManager;

    @Inject
    private CustomerManager customerManager;
}
