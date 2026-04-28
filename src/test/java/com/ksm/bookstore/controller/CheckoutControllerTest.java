package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.CartForm;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.service.OrderService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
        FacesContext facesContext;

        @Mock
        ExternalContext externalContext;

        @Spy
        CartForm cartForm;

        final List<Book> cartItems;

        final Long orderNumber = 42L;

        public Mocking() {
            openMocks(this);
            when(facesContext.getExternalContext()).thenReturn(externalContext);
            cartForm.init();
            cartItems = cartForm.getCartItems();
            when(orderService.submitOrder(any())).thenReturn(orderNumber);
        }
    }

    // submitOrder() tests

    @Test(description = "submitOrder() should call orderService.submitOrder() with the cart items")
    public void submitOrder_callsOrderServiceWithCartItems() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.submitOrder();

        // Verify
        verify(m.orderService).submitOrder(m.cartItems);
    }

    @Test(description = "submitOrder() should clear the cart after the order is placed")
    public void submitOrder_clearsCartAfterSubmission() throws IOException {
        // Arrange
        Mocking m = new Mocking();
        m.cartItems.add(new Book());

        // Act
        m.controller.submitOrder();

        // Assert
        assertTrue(m.cartItems.isEmpty());
    }

    @Test(description = "submitOrder() should redirect to the confirmation page with the order number in the URL")
    public void submitOrder_redirectesToConfirmationPage() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.submitOrder();

        // Verify
        verify(m.externalContext).redirect("confirmation.jsf?orderNumber=42");
    }
}