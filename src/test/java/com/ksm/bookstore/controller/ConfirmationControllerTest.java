package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.OrderItemManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.ConfirmationForm;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.jpa.OrderItem;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link ConfirmationController}
 */
public class ConfirmationControllerTest {

    private static final class Mocking {

        @InjectMocks
        ConfirmationController controller;

        @Mock
        OrderManager orderManager;

        @Mock
        OrderItemManager orderItemManager;

        @Mock
        FacesContext facesContext;

        @Mock
        ExternalContext externalContext;

        @Spy
        ConfirmationForm confirmationForm;

        final Order order = new Order();

        final List<OrderItem> orderItems = List.of(new OrderItem(), new OrderItem());

        final Long orderNumber = 123L;

        public Mocking() {
            openMocks(this);
            when(facesContext.getExternalContext()).thenReturn(externalContext);
            confirmationForm.setOrderNumber(orderNumber);
            when(orderManager.findById(orderNumber)).thenReturn(order);
            confirmationForm.setOrder(order);
            when(orderItemManager.findByOrder(order)).thenReturn(orderItems);
        }
    }

    // init() test - null order number

    @Test(description = "init() should redirect to the home page when no order number exists")
    public void init_redirectsToHomeWhenOrderNumberIsNull() throws IOException {
        // Arrange
        Mocking m = new Mocking();
        m.confirmationForm.setOrderNumber(null);
        when(m.externalContext.getRequestContextPath()).thenReturn("");

        // Act
        m.controller.init();

        // Verify
        verify(m.externalContext).redirect("/pages/public/home.jsf");
    }

    // init() tests - valid order number

    @Test(description = "init() should load the order from the manager using the order number from the form")
    public void init_loadsOrderByOrderNumber() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.init();

        // Verify
        verify(m.orderManager).findById(m.orderNumber);
        
        // Assert
        assertEquals(m.confirmationForm.getOrder(), m.order);
    }

    @Test(description = "init() should load the order items for the retrieved order")
    public void init_loadsOrderItemsByOrder() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.init();

        // Verify
        verify(m.orderItemManager).findByOrder(m.order);
        
        // Assert
        assertEquals(m.confirmationForm.getOrderItems(), m.orderItems);
    }
}