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

    public OrderManager() {
        super(Order.class);
    }

    public List<Order> findByStatus(OrderStatus status) {
        String query = "SELECT s FROM Order s WHERE s.orderStatus = :status";
        return entityManager.createQuery(query, Order.class)
                .setParameter("status", status)
                .getResultList();
    }
    
}


/*         [A]           [B]          [C]
* public List<Order> findByStatus(OrderStatus status) {
*                                      [D]  [E]         [F]            [G]
*        String query = "SELECT s FROM Order s WHERE s.orderStatus = :status";
*                                         [H]        [I]
*        return entityManager.createQuery(query, Order.class)
*                                [J]       [K]
*                .setParameter("status", status)
*                       [L]
*                .getResultList();
*    }
*
*[A] List<Order> — return type, a list of Order objects
*[B] findByStatus — method name
*[C] OrderStatus status — the parameter, an enum value being passed in as the search filter
*[D] Order — the entity class being searched through in the database
*[E] s — nickname/alias for Order in the query
*[F] s.orderStatus — the field on the Order entity being compared
*[G] :status — the placeholder blank to be filled in
*[H] query — the JPQL string being passed to the entity manager
*[I] Order.class — tells Hibernate what type to return
*[J] "status" — matches the placeholder name at [G]
*[K] status — the actual enum value passed into the method at [C], fills in the blank
*[L] getResultList() — returns multiple results as a list
*
*/