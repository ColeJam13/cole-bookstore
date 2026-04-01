package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Address;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Address
 * 
 */

@Stateless
public class AddressManager extends BaseManager<Address>{
    
    public AddressManager() {
        super(Address.class);
    }
}
