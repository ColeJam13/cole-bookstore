package com.ksm.bookstore.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.ksm.bookstore.dao.AddressManager;
import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.dao.OrderItemManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.CheckoutForm;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.jpa.OrderItem;
import com.ksm.bookstore.model.OrderStatus;
import com.ksm.bookstore.jpa.Address;
import com.ksm.bookstore.jpa.Book;

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
    private AddressManager addressManager;

    @Inject
    private OrderItemManager orderItemManager;

    @Inject
    private CheckoutForm checkoutForm;

    /**
     * Submits a customer order by creating or identifying the customer,
     * persisting their addresses, creating the order, and saving each
     * book in the cart as an order item.
     *
     * @param cartItems the list of books the customer is purchasing
     * @return the order number of the newly created order
     */
    public Long submitOrder(List<Book> cartItems) {
        Customer customer = customerManager.findByEmail(checkoutForm.getEmail());

            if (customer == null) {
                Address shippingAddress = new Address();
                shippingAddress.setStreet(checkoutForm.getStreetAddress());
                shippingAddress.setCity(checkoutForm.getCity());
                shippingAddress.setState(checkoutForm.getState());
                shippingAddress.setZip(checkoutForm.getZip());
                addressManager.create(shippingAddress);

                Address billingAddress;
                if (checkoutForm.isSameAsShipping()) {
                    billingAddress = shippingAddress;
                } else {
                    billingAddress = new Address();
                    billingAddress.setStreet(checkoutForm.getBillingStreetAddress());
                    billingAddress.setCity(checkoutForm.getBillingCity());
                    billingAddress.setState(checkoutForm.getBillingState());
                    billingAddress.setZip(checkoutForm.getBillingZip());
                    addressManager.create(billingAddress);
                }

                customer = new Customer();
                    customer.setFirstName(checkoutForm.getFirstName());
                    customer.setLastName(checkoutForm.getLastName());
                    customer.setEmail(checkoutForm.getEmail());
                    customer.setShippingAddress(shippingAddress);
                    customer.setBillingAddress(billingAddress);

                customerManager.create(customer);
            }
        
            Order order = new Order();
            order.setCustomer(customer);
            BigDecimal orderTotal = BigDecimal.ZERO;
                for (Book book : cartItems) {
                    orderTotal = orderTotal.add(book.getPrice());
                }

            order.setOrderStatus(OrderStatus.SUBMITTED);
            order.setOrderTotal(orderTotal);
            orderManager.create(order);
                for (Book book : cartItems) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setPrice(book.getPrice());
                    orderItem.setBook(book);
                    orderItem.setQuantity(1);

                    orderItemManager.create(orderItem);
                }
            return order.getOrderNumber();
    }
}
