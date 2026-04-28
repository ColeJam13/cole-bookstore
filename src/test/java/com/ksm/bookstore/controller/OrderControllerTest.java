package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.OrderForm;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.model.OrderStatus;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link OrderController}
 */
public class OrderControllerTest {

    private static final class Mocking {

        @InjectMocks
        OrderController controller;

        @Mock
        OrderManager orderManager;

        @Mock
        OrderForm orderForm;

        final Order order = new Order();

        final List<Order> refreshedOrders = List.of(new Order(), new Order());

        public Mocking() {
            openMocks(this);
            when(orderManager.findAll()).thenReturn(refreshedOrders);
        }
    }

    // updateOrderStatus() tests

    @Test(description= "updateOrderStatus() should set the given status on the order")
    public void updateOrderStatus_setsStatusOnOrder() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.updateOrderStatus(m.order, OrderStatus.COMPLETE);

        // Assert
        assertEquals(m.order.getOrderStatus(), OrderStatus.COMPLETE);
    }

    @Test(description = "updateOrderStatus() should persist the order via the order manager")
    public void updateOrderStatus_persistsOrder() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.updateOrderStatus(m.order, OrderStatus.CANCELLED);

        // Verify
        verify(m.orderManager).update(m.order);
    }

    @Test(description = "updateOrderStatus() should refresh the order list on the form")
    public void updateOrderStatus_refreshesOrderListOnForm() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.updateOrderStatus(m.order, OrderStatus.SUBMITTED);

        // Verify
        verify(m.orderForm).setOrderList(m.refreshedOrders);
    }
}