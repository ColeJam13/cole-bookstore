package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.model.OrderStatus;

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
 * Unit tests for {@link OrderManager}
 */
public class OrderManagerTest {

    private static final class Mocking {

        @InjectMocks
        OrderManager orderManager;

        @Mock
        EntityManager entityManager;

        @Mock
        TypedQuery<Order> orderQuery;

        Customer customer = new Customer();
        Order order = new Order();

        public Mocking() {
            openMocks(this);
            when(entityManager.createQuery(anyString(), eq(Order.class))).thenReturn(orderQuery);
            when(orderQuery.setParameter(anyString(), any())).thenReturn(orderQuery);
            when(orderQuery.getResultList()).thenReturn(List.of(order));
        }
    }

    // findByStatus() tests

    @Test(description = "findByStatus() should return orders matching the given status")
    public void findByStatus_returnsOrdersWithMatchingStatus() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        List<Order> result = m.orderManager.findByStatus(OrderStatus.SUBMITTED);

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), m.order);
    }

    @Test(description = "findByStatus() should return an empty list when no orders match the given status")
    public void findByStatus_returnsEmptyListWhenNoneMatch() {
        // Arrange
        Mocking m = new Mocking();
        when(m.orderQuery.getResultList()).thenReturn(List.of());

        // Act
        List<Order> result = m.orderManager.findByStatus(OrderStatus.SUBMITTED);

        // Assert
        assertTrue(result.isEmpty());
    }

    // findByCustomer() tests

    @Test(description = "findByCustomer() should return all orders belonging to the given customer")
    public void findByCustomer_returnsOrdersForCustomer() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        List<Order> result = m.orderManager.findByCustomer(m.customer);

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), m.order);
    }

    @Test(description = "findByCustomer() should return an empty list when the customer has no orders")
    public void findByCustomer_returnsEmptyListWhenCustomerHasNoOrders() {
        // Arrange
        Mocking m = new Mocking();
        when(m.orderQuery.getResultList()).thenReturn(List.of());

        // Act
        List<Order> result = m.orderManager.findByCustomer(m.customer);

        // Assert
        assertTrue(result.isEmpty());
    }
}