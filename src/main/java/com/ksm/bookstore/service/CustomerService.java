package com.ksm.bookstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.CustomerManager;

/**
 * Service class containing business logic for Customer related operations.
 * Acts as an intermediary between CustomerManager and the controller layer.
 */

@ApplicationScoped
public class CustomerService {

    @Inject
    private CustomerManager customerManager;
    
}
