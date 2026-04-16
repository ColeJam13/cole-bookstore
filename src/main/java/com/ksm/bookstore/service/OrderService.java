package com.ksm.bookstore.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.dao.OrderItemManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.CheckoutForm;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.jpa.OrderItem;
import com.ksm.bookstore.model.OrderStatus;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.jpa.Customer;

/**
 * Service class containing business logic for Order related operations.
 * Acts as an intermediary between OrderManager and CustomerManager and the controller layer.
 */

@Stateless
public class OrderService {
    
    @Inject
    private OrderManager orderManager;

    @Inject
    private CustomerManager customerManager;

    @Inject
    private OrderItemManager orderItemManager;

    @Inject
    private CheckoutForm checkoutForm;

    /**
     * Private method that ensures that the billing address fields get filled
     * if the "Same As Shipping" box is filled, preventing null errors
     */
    private void resolveBillingAddress() {
        if (checkoutForm.isSameAsShipping()) {
            checkoutForm.getCustomer().setBillingAddress(
                checkoutForm.getCustomer().getShippingAddress());
        }
    }

    /**
     * Submits a customer order by saving the customer, creating
     * the order, and saving each book in the cart as an order item
     *
     * @param cartItems the list of books the customer is purchasing
     * @return the order number of the newly created order
     */
    public Long submitOrder(List<Book> cartItems) {
        resolveBillingAddress();
        Customer customer = customerManager.update(checkoutForm.getCustomer());  

        Order order = new Order();
        order.setCustomer(customer);
        BigDecimal orderTotal = BigDecimal.ZERO;
            for (Book book : cartItems) {
                orderTotal = orderTotal.add(book.getPrice());
            }

        order.setOrderStatus(OrderStatus.SUBMITTED);
        order.setOrderTotal(orderTotal);
        order = orderManager.update(order);
            for (Book book : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setPrice(book.getPrice());
                orderItem.setBook(book);
                orderItem.setQuantity(1);
                orderItemManager.update(orderItem);
            }
        return order.getOrderNumber();
    }
}
