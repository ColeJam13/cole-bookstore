package com.ksm.bookstore.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.ksm.bookstore.dao.OrderItemManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.ConfirmationForm;

/**
 * Controller for the Confirmation page. Handles grabbing order details
 * and giving the customer a summary of their purchase.
 */

@Named
@RequestScoped
public class ConfirmationController {
    
    @Inject
    private OrderManager orderManager;

    @Inject
    private OrderItemManager orderItemManager;

    @Inject
    private ConfirmationForm confirmationForm;

    /**
     * Method that fills in the confirmation page with the completed
     * order determined via Order Number. Prevents edge case of admin login
     * from the checkout screen failing due to no order number: redirects home
     */
    public void init() throws IOException {
        if (confirmationForm.getOrderNumber() == null) {
            FacesContext.getCurrentInstance().getExternalContext()
                .redirect(FacesContext.getCurrentInstance().getExternalContext()
                .getRequestContextPath() + "/pages/public/home.jsf");
            return;
        }
        confirmationForm.setOrder(orderManager.findById(confirmationForm.getOrderNumber()));
        confirmationForm.setOrderItems(orderItemManager.findByOrder(confirmationForm.getOrder()));
    }
}
