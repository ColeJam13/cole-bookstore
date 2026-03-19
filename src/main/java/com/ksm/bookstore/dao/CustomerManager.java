package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Customer;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Customer objects
 * 
 */

@Stateless
public class CustomerManager extends BaseManager<Customer>{

    private static final String QUERY_FIND_BY_EMAIL = "SELECT e FROM Customer e WHERE e.email = :email";


    public CustomerManager() {
        super(Customer.class);
    }

    /**
     * Finds a single customer by their email address
     *
     * @param email the email address to search for
     * @return the matching Customer entity
     */
    public Customer findByEmail(String email) {
        return entityManager.createQuery(QUERY_FIND_BY_EMAIL, Customer.class)
                .setParameter("email", email)
                .getSingleResult();
    }
    
}
