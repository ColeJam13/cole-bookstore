package com.ksm.bookstore.dao;

import com.ksm.bookstore.model.OrderStatus;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.jpa.Order;

import java.util.List;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Order objects
 * 
 */

@Stateless
public class OrderManager extends BaseManager<Order>{

    private static final String QUERY_FIND_BY_STATUS = "SELECT o FROM Order o WHERE o.orderStatus = :status";

    private static final String QUERY_FIND_BY_CUSTOMER = "SELECT DISTINCT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.book WHERE o.customer = :customer";
    
    public OrderManager() {
        super(Order.class);
    }

    /**
     * Finds all orders matching a given status
     *
     * @param status the OrderStatus to filter by
     * @return a list of orders with the given status
     */
    public List<Order> findByStatus(OrderStatus status) {
        return entityManager.createQuery(QUERY_FIND_BY_STATUS, Order.class)
                .setParameter("status", status)
                .getResultList();
    }

    /**
     * Finds all orders from an existing customer
     * @param customer the customer whose order we're retrieving 
     * @return a list of previous orders from a customer
     */
    public List<Order> findByCustomer(Customer customer) {
        return entityManager.createQuery(QUERY_FIND_BY_CUSTOMER, Order.class)
                .setParameter("customer", customer)
                .getResultList();
    }
    
}