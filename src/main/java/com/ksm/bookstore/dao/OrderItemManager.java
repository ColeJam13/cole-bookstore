package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.OrderItem;
import com.ksm.bookstore.jpa.Order;

import java.util.List;

import javax.ejb.Stateless;

/**
 * DAO for accessing/updating information on Order Items
 */

@Stateless
public class OrderItemManager extends BaseManager<OrderItem> {

    private static final String QUERY_FIND_BY_ORDER = "SELECT oi FROM OrderItem oi WHERE oi.order= :order";

    public OrderItemManager() {
        super(OrderItem.class);
    }

    /**
     * Get the list of OrderItems in a customers order
     * @param order
     * @return list of OrderItems in order
     */
    public List<OrderItem> findByOrder(Order order) {
            return entityManager.createQuery(QUERY_FIND_BY_ORDER, OrderItem.class)
                    .setParameter("order", order)
                    .getResultList();
        }    
}
