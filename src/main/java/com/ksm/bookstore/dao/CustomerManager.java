package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Customer;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Customer objects
 * 
 */

@Stateless
public class CustomerManager extends BaseManager<Customer>{

    public CustomerManager() {
        super(Customer.class);
    }

    public Customer findByEmail(String email) {
        String query = "SELECT e FROM Customer e WHERE e.email = :email";
        return entityManager.createQuery(query, Customer.class)
                .setParameter("email", email)
                .getSingleResult();
    }
    
}
