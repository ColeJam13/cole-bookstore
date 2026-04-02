package com.ksm.bookstore.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ksm.bookstore.dao.OrderItemManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.ConfirmationForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Controller for the Confirmation page. Handles grabbing order details
 * and giving the customer a summary of their purchase.
 */

@Named
@RequestScoped
@Getter
@Setter
public class ConfirmationController implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Inject
    private OrderManager orderManager;

    @Inject
    private OrderItemManager orderItemManager;

    @Inject
    private ConfirmationForm confirmationForm;

    private Long orderNumber;

    /**
     * Method that fills in the confirmation page with the completed
     * order determined via Order Number
     */
    public void init() {
        confirmationForm.setOrder(orderManager.findById(orderNumber));
        confirmationForm.setOrderItems(orderItemManager.findByOrder(confirmationForm.getOrder()));
    }
}
