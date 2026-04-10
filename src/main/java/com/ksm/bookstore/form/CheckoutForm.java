package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.omnifaces.cdi.ViewScoped;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.jpa.Address;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.provider.UserProvider;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Form that holds the view state data for the checkout page
 */
@Named
@ViewScoped
@Getter
@Setter
public class CheckoutForm implements Serializable{

    private static final long serialVersionUID = 1L;

    private Customer customer;

    private boolean sameAsShipping;

    @Inject
    private UserProvider userProvider;

    @Inject
    private CustomerManager customerManager;

    /**
     * checks to see if there is an existing user for the entered username/email
     * if so, finds that customer and populates the information in the form. If not
     * found, creates new customer with new address fields
     */
    @PostConstruct
    public void init() {
        String username = userProvider.getUserName();
        customer = Optional.ofNullable(customerManager.findByEmail(username))
                    .orElse(createCustomer());
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setShippingAddress(new Address());
        customer.setBillingAddress(new Address());
        return customer;
    }
}
