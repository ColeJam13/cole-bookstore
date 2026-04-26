package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.jpa.OrderItem;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link OrderItemManager}
 */
public class OrderItemManagerTest {

    private static final class Mocking {

        @InjectMocks
        OrderItemManager orderItemManager;

        @Mock
        EntityManager entityManager;

        @Mock
        TypedQuery<OrderItem> orderItemQuery;

        Order order = new Order();
        OrderItem orderItem = new OrderItem();

        public Mocking() {
            openMocks(this);
            when(entityManager.createQuery(anyString(), eq(OrderItem.class))).thenReturn(orderItemQuery);
            when(orderItemQuery.setParameter(anyString(), any())).thenReturn(orderItemQuery);
            when(orderItemQuery.getResultList()).thenReturn(List.of(orderItem));
        }
    }

    // findByOrder() tests

    @Test(description = "findByOrder() should return all order items belonging to the given order")
    public void findByOrder_returnsOrderItemsForOrder() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        List<OrderItem> result = m.orderItemManager.findByOrder(m.order);

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), m.orderItem);
    }

    @Test(description = "findByOrder() should return an empty list when the order has no Items")
    public void findByOrder_returnsEmptyListWhenOrderHasNoItems() {
        // Arrange
        Mocking m = new Mocking();
        when(m.orderItemQuery.getResultList()).thenReturn(List.of());

        // Act
        List<OrderItem> result = m.orderItemManager.findByOrder(m.order);

        // Assert
        assertTrue(result.isEmpty());
    }
}