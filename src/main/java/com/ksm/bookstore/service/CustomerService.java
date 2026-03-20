package com.ksm.bookstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.CustomerManager;


@ApplicationScoped
public class CustomerService {

    @Inject
    private CustomerManager customerManager;
    
}
