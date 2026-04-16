package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.jpa.Address;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.provider.UserProvider;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Form that holds the view state data for the user profile page
 */
@Named
@ViewScoped
@Getter
@Setter
public class ProfileForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Customer customer;

    private List<Order> orderHistory;

    @Inject
    private UserProvider userProvider;

    @Inject
    private CustomerManager customerManager;

    @Inject
    private OrderManager orderManager;

    private boolean sameAsShipping;
    
    /**
     * Checks to see that a user profile exists, creates a new customer 
     * if email returns an empty field. Creates an array list of items
     * that the current user has ordered
     */
    @PostConstruct
    public void init() {
        customer = Optional.ofNullable(customerManager.findByEmail(userProvider.getUserName()))
                    .orElseGet(this::createCustomer);
        orderHistory = orderManager.findByCustomer(customer);
    }

    /**
     * Creates a new customer if not found in the current database
     * @return a new customer with an empty address field and their email pre-populated
     */
    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setEmail(userProvider.getUserName());
        customer.setShippingAddress(new Address());
        customer.setBillingAddress(new Address());
        return customer;
    }
}
