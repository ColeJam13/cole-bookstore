package com.ksm.bookstore.dao;

import com.ksm.bookstore.model.OrderStatus;
import com.ksm.bookstore.jpa.Order;

import java.util.List;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Order objects
 * 
 */

@Stateless
public class OrderManager extends BaseManager<Order>{

    private static final String QUERY_FIND_BY_STATUS = "SELECT s FROM Order s WHERE s.orderStatus = :status";


    public OrderManager() {
        super(Order.class);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return entityManager.createQuery(QUERY_FIND_BY_STATUS, Order.class)
                .setParameter("status", status)
                .getResultList();
    }
    
}