package com.ksm.bookstore.service;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
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

@ApplicationScoped
public class OrderService {
    
    @Inject
    private OrderManager orderManager;

    @Inject
    private CustomerManager customerManager;

    @Inject
    private AddressManager addressManager;

    @Inject
    private OrderItemManager orderItemManager;

    /**
     * Submits a customer order by creating or identifying the customer,
     * persisting their addresses, creating the order, and saving each
     * book in the cart as an order item.
     *
     * @param form the checkout form containing customer and address information
     * @param cartItems the list of books the customer is purchasing
     * @return the order number of the newly created order
     */
    public Long submitOrder(CheckoutForm form, List<Book> cartItems) {
        Customer customer = customerManager.findByEmail(form.getEmail());

            if (customer == null) {
                Address shippingAddress = new Address();
                shippingAddress.setStreet(form.getStreetAddress());
                shippingAddress.setCity(form.getCity());
                shippingAddress.setState(form.getState());
                shippingAddress.setZip(form.getZip());
                addressManager.create(shippingAddress);

                Address billingAddress;
                if (form.isSameAsShipping()) {
                    billingAddress = shippingAddress;
                } else {
                    billingAddress = new Address();
                    billingAddress.setStreet(form.getBillingStreetAddress());
                    billingAddress.setCity(form.getBillingCity());
                    billingAddress.setState(form.getBillingState());
                    billingAddress.setZip(form.getBillingZip());
                    addressManager.create(billingAddress);
                }

                customer = new Customer();
                    customer.setFirstName(form.getFirstName());
                    customer.setLastName(form.getLastName());
                    customer.setEmail(form.getEmail());
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
