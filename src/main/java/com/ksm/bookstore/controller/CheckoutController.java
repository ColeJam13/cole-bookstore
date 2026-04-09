package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.CartForm;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

import com.ksm.bookstore.service.OrderService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the checkout page of the bookstore application.
 * Handles order submission and customer identification for public users.
 * Communicates with OrderService to process purchases.
 */

@Named
@RequestScoped 
public class CheckoutController {
    
    @Inject
    private OrderService orderService;

    @Inject
    private CartForm cartForm;

    /**
     * Method that submits the order within the cart. Generates an order number
     * to search by later on, clears the card, redirects you to a confirmation screen
     * @throws IOException
     */
    public void submitOrder() throws IOException {
        Long orderNumber = orderService.submitOrder(cartForm.getCartItems());
        cartForm.getCartItems().clear();
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .redirect("confirmation.jsf?orderNumber=" + orderNumber);
    }
}