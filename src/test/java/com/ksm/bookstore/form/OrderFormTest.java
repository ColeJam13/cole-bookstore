package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.jpa.Order;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link OrderForm}
 */
public class OrderFormTest {

    private static final class Mocking {

        @InjectMocks
        OrderForm form;

        @Mock
        OrderManager orderManager;

        final List<Order> orders = List.of(new Order(), new Order());

        public Mocking() {
            openMocks(this);
            when(orderManager.findAll()).thenReturn(orders);
        }
    }

    // init() tests

    @Test(description = "init() should populate orderList with all the orders returned by the order manager")
    public void init_orderListPopulatedFromManager() {
        //Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getOrderList(), m.orders);

        // Verify
        verify(m.orderManager).findAll();
    }

    @Test(description = "init() should set orderList to a non-null empty list when no orders exist")
    public void init_emptyListWhenNoOrdersExist() {
        // Arrange
        Mocking m = new Mocking();
        when(m.orderManager.findAll()).thenReturn(new ArrayList<>());

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getOrderList());
        assertTrue(m.form.getOrderList().isEmpty());
    }

    @Test(description = "init() should propagate exceptions thrown by the order manager",
        expectedExceptions = RuntimeException.class)
    public void init_propagatesExceptionFromManager() {
        // Arrange
        Mocking m = new Mocking();
        when(m.orderManager.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act
        m.form.init();
    }

}