package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.CartForm;
import com.ksm.bookstore.jpa.Book;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link CartController}
 */
public class CartControllerTest {

    private static final class Mocking {

        @InjectMocks
        CartController controller;

        @Mock
        CartForm cartForm;

        final List<Book> cartItems = new ArrayList<>();

        final Book book = new Book();

        public Mocking() {
            openMocks(this);
            when(cartForm.getCartItems()).thenReturn(cartItems);
        }

        // Method that wires up the FacesContext/ExternalContext mock chain that every mockStatic test needs
        ExternalContext setupFacesMocks(MockedStatic<FacesContext> mockedFaces) {
            FacesContext facesContextMock = mock(FacesContext.class);
            ExternalContext externalContextMock = mock(ExternalContext.class);
            mockedFaces.when(FacesContext::getCurrentInstance).thenReturn(facesContextMock);
            when(facesContextMock.getExternalContext()).thenReturn(externalContextMock);
            return externalContextMock;
        }
    }

    // addToCart() test

    @Test(description = "addToCart() should add the given book to the cart items list")
    public void addToCart_addsBookToCartIems() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.addToCart(m.book);

        // Assert
        assertTrue(m.cartItems.contains(m.book));
    }

    // removeFromCart() tests

    @Test(description = "removeFromCart() should remove the given book from the cart items list")
    public void removeFromCart_removesBookFromCartItems() throws IOException {
        // Arrange
        Mocking m = new Mocking();
        m.cartItems.add(m.book);

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.removeFromCart(m.book);

            // Assert
            assertFalse(m.cartItems.contains(m.book));
        }
    }

    @Test(description = "removeFromCart() should redirect to the cart page after removing the book")
    public void removeFromCart_redirectsToCartPage() throws IOException {
        // Arrange
        Mocking m = new Mocking();
        m.cartItems.add(m.book);

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.removeFromCart(m.book);

            // Verify
            verify(externalContextMock).redirect("cart.jsf");
        }
    }

    // clearCart() tests

    @Test(description = "clearCart() should remove all items from the cart")
    public void clearCart_removesAllItemsFromCart() throws IOException {
        // Arrange
        Mocking m = new Mocking();
        m.cartItems.add(new Book());
        m.cartItems.add(new Book());

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.clearCart();

            // Assert
            assertTrue(m.cartItems.isEmpty());
        }
    }

    @Test(description = "clearCart() should redirect to the cart page after clearing")
    public void clearCart_redirectsToCartPage() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            ExternalContext externalContextMock = m.setupFacesMocks(mockedFaces);

            // Act
            m.controller.clearCart();

            // Verify
            verify(externalContextMock).redirect("cart.jsf");
        }
    }
}