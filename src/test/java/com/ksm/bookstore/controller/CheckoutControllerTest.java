package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.CartForm;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.service.OrderService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link CheckoutController}
 */
public class CheckoutControllerTest {

    private static final class Mocking {

        @InjectMocks
        CheckoutController controller;

        @Mock
        OrderService orderService;

        @Mock
        CartForm cartForm;

        List<Book> cartItems = new ArrayList<>();

        Long orderNumber = 42L;

        public Mocking() {
            openMocks(this);
            when(cartForm.getCartItems()).thenReturn(cartItems);
            when(orderService.submitOrder(any())).thenReturn(orderNumber);
        }

        // Helper method that wires up the FacesContext/ExternalContext mock chain that every mockStatic test needs
        ExternalContext setupFacesMocks(MockedStatic<FacesContext> mockedFaces) {
            FacesContext facesContextMock = mock(FacesContext.class);
            ExternalContext externalContextMock = mock(ExternalContext.class);
            mockedFaces.when(FacesContext::getCurrentInstance).thenReturn(facesContextMock);
            when(facesContextMock.getExternalContext()).thenReturn(externalContextMock);
            return externalContextMock;
        }
    }

    // submitOrder() tests

    @Test(description = "submitOrder() should call orderService.submitOrder() with the cart items")
    public void submitOrder_callsOrderServiceWithCartItems() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.submitOrder();

            // Verify
            verify(m.orderService).submitOrder(m.cartItems);
        }
    }

    @Test(description = "submitOrder() should clear the cart after the order is placed")
    public void submitOrder_clearsCartAfterSubmission() throws IOException {
        // Arrange
        Mocking m = new Mocking();
        m.cartItems.add(new Book());

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.submitOrder();

            // Assert
            assertTrue(m.cartItems.isEmpty());
        }
    }

    @Test(description = "submitOrder() should redirect to the confirmation page with the order number in the URL")
    public void submitOrder_redirectesToConfirmationPage() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.submitOrder();

            // Verify
            verify(externalContextMock).redirect("confirmation.jsf?orderNumber=" + m.orderNumber);
        }
    }
}