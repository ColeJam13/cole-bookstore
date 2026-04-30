package com.ksm.bookstore.controller;

import javax.enterprise.context.RequestScoped;

import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.OrderForm;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.model.OrderStatus;


import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for managing individual orders within the admin section.
 * Handles order status updates such as cancellation and completion for administrators.
 */

@Named
@RequestScoped
public class OrderController {

    @Inject
    private OrderForm orderForm; 

    @Inject
    private OrderManager orderManager;
    
    /**
     * Method that controls setting and updating the order status, and 
     * refreshes the list to show that new update
     * @param order the order selected
     * @param status the status to change the order to
     */
    public void updateOrderStatus(Order order, OrderStatus status) {
        order.setOrderStatus(status);
        orderManager.update(order);
        orderForm.init();
    }
}