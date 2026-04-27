package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.OrderItemManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.ConfirmationForm;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.jpa.OrderItem;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

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
        ConfirmationForm confirmationForm;

        Order order = new Order();
        List<OrderItem> orderItems = List.of(new OrderItem(), new OrderItem());

        Long orderNumber = 123L;

        public Mocking() {
            openMocks(this);
            when(confirmationForm.getOrderNumber()).thenReturn(orderNumber);
            when(orderManager.findById(orderNumber)).thenReturn(order);
            when(confirmationForm.getOrder()).thenReturn(order);
            when(orderItemManager.findByOrder(order)).thenReturn(orderItems);
        }

        // helper method that wires up the FacesContext/ExternalContext mock chain
        ExternalContext setupFacesMocks(MockedStatic<FacesContext> mockedFaces) {
            FacesContext facesContextMock = mock(FacesContext.class);
            ExternalContext externalContextMock = mock(ExternalContext.class);
            mockedFaces.when(FacesContext::getCurrentInstance).thenReturn(facesContextMock);
            when(facesContextMock.getExternalContext()).thenReturn(externalContextMock);
            return externalContextMock;
        }
    }

    // init() test - null order number

    @Test(description = "init() should redirect to the home page when no order number exists")
    public void init_redirectsToHomeWhenOrderNumberIsNull() throws IOException {
        // Arrange
        Mocking m = new Mocking();
        when(m.confirmationForm.getOrderNumber()).thenReturn(null);

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);
            when(externalContextMock.getRequestContextPath()).thenReturn("");

            // Act
            m.controller.init();

            // Verify
            verify(externalContextMock).redirect("/pages/public/home.jsf");
        }
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
        verify(m.confirmationForm).setOrder(m.order);
    }

    @Test(description = "init() should load the order items for the retrieved order")
    public void init_loadsOrderItemsByOrder() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.init();

        // Verify
        verify(m.orderItemManager).findByOrder(m.order);
        verify(m.confirmationForm).setOrderItems(m.orderItems);
    }
}