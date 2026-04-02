package com.ksm.bookstore.controller;

import com.ksm.bookstore.jpa.Address;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.form.CheckoutForm;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

import com.ksm.bookstore.service.OrderService;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the checkout page of the bookstore application.
 * Handles order submission and customer identification for public users.
 * Communicates with OrderService and CustomerService to process purchases.
 */

@Named
@RequestScoped 
@Getter
@Setter
public class CheckoutController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private OrderService orderService;

    @Inject
    private CustomerManager customerManager;

    @Inject
    private CheckoutForm checkoutForm;

    @Inject
    private CartController cartController;

    /**
     * Helper Method that will autofill the shipping address if it is
     * determined the customer is a returning customer
     * 
     * @param address
     */
    private void prefillShippingAddress(Address address) {
        checkoutForm.setStreetAddress(address.getStreet());
        checkoutForm.setCity(address.getCity());
        checkoutForm.setState(address.getState());
        checkoutForm.setZip(address.getZip());
    }

    /**
     * Helper Method that will autofill the billing address if it is
     * determined the customer is a returning customer
     * 
     * @param address
     */
    private void prefillBillingAddress(Address address) {
        checkoutForm.setBillingStreetAddress(address.getStreet());
        checkoutForm.setBillingCity(address.getCity());
        checkoutForm.setBillingState(address.getState());
        checkoutForm.setBillingZip(address.getZip());
    }

    /**
     * Method that will determine if a customer already exists by
     * looking up their email address. If found, it will autofill 
     * the customers information
     */
    public void lookupCustomer() {
        Customer customer = customerManager.findByEmail(checkoutForm.getEmail());
        
        if (customer != null) {
            checkoutForm.setFirstName(customer.getFirstName());
            checkoutForm.setLastName(customer.getLastName());
                if (customer.getShippingAddress() != null) {
                    prefillShippingAddress(customer.getShippingAddress());
                }
                if (customer.getBillingAddress() != null) {
                    prefillBillingAddress(customer.getBillingAddress());
                }
            }
        }

    /**
     * Method that submits the order within the cart. Generates an order number
     * to search by later on, clears the card, redirects you to a confirmation screen
     * @throws IOException
     */
    public void submitOrder() throws IOException {
        Long orderNumber = orderService.submitOrder(checkoutForm, cartController.getCartItems());
        cartController.clearCart();
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .redirect("confirmation.jsf?orderNumber=" + orderNumber);
    }
}