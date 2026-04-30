package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

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
public class CheckoutForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Customer customer;

    private boolean sameAsShipping;

    private boolean promptShown;

    @Inject
    private UserProvider userProvider;

    @Inject
    private CustomerManager customerManager;

    /**
     * checks to see if a JAAS user is logged in, if so their email is used to
     * look up an existing customer, otherwise a fresh empty customer is created
     */
    @PostConstruct
    public void init() {
        customer = Optional.ofNullable(userProvider.getUserName())
                    .map(customerManager::findByEmail)
                    .orElseGet(this::createCustomer);
    }

    /**
     * Creates a new customer and initializes the address fields
     * @return the new customer
     */
    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setShippingAddress(new Address());
        customer.setBillingAddress(new Address());
        return customer;
    }

    /**
     * Marks the welcome prompt as shown so it doesnt reappear on
     * subsequent renders after a login error
     */
    public void markPromptShown() {
        promptShown = true;
    }
}
