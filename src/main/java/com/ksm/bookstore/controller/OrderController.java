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
     * Method that allows an admin to cancel an order (updates its status)
     * @param order
     */
    public void cancelOrder(Order order) {
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderManager.update(order);
        orderForm.setOrderList(orderManager.findAll());
    }

    /**
     * Method that allows an admin to complete an order (updates its status)
     * @param order
     */
    public void completeOrder(Order order) {
        order.setOrderStatus(OrderStatus.COMPLETE);
        orderManager.update(order);
        orderForm.setOrderList(orderManager.findAll());
    }

    /**
     * Method that allows an admin to change an orders status to submitted (protects edge case)
     * @param order
     */
    public void submitOrder(Order order) {
        order.setOrderStatus(OrderStatus.SUBMITTED);
        orderManager.update(order);
        orderForm.setOrderList(orderManager.findAll());
    }
}