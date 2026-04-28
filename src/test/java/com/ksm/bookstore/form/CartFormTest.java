package com.ksm.bookstore.form;

import com.ksm.bookstore.jpa.Book;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Unit tests for {@link CartForm}
 */
public class CartFormTest {
    private static final class Mocking {

        @InjectMocks
        CartForm form;

        public Mocking() {
            openMocks(this);
        }

        // Helper method that creates a Book with only a price set
        public Book createBookWithPrice(String price) {
            Book book = new Book();
            book.setPrice(new BigDecimal(price));
            return book;
        }
    }

    // init() test

    @Test(description = "init() should produce a non-null empty list - cart must start empty, not null")
    public void init_cartStartsEmpty() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertTrue(m.form.getCartItems().isEmpty());
    }

    // getSubTotal() tests

    @Test(description = "getSubtotal() should return 0.0 when cart is empty")
    public void getSubTotal_emptyCartReturnsZero() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getSubTotal(), 0.0, 0.001);
    }

    @Test(description = "getSubTotal() should correctly sum the prices of all books in the cart")
    public void getSubTotal_returnsCorrectSumForMultipleBooks() {
        // Arrange
        Mocking m = new Mocking();
        m.form.init();
        m.form.getCartItems().add(m.createBookWithPrice("10.00"));
        m.form.getCartItems().add(m.createBookWithPrice("15.50"));
        m.form.getCartItems().add(m.createBookWithPrice("9.99"));

        // Assert
        assertEquals(m.form.getSubTotal(), 35.49, 0.001);
    }

    @Test(description = "getSubTotal() should return the single book's price when the cart has one item")
    public void getSubTotal_singleBookReturnsItsPrice() {
        // Arrange
        Mocking m = new Mocking();
        m.form.init();
        m.form.getCartItems().add(m.createBookWithPrice("24.99"));

        // Assert
        assertEquals(m.form.getSubTotal(), 24.99, 0.001);
    }

    // getTax() tests

    @Test(description = "getTax() should return 0.0 when the cart is empty")
    public void getTax_emptyCartReturnsZero() {
        // Arrange
        Mocking m = new Mocking();
        m.form.setCartItems(Collections.emptyList());

        // Act
        double actual = m.form.getTax();

        // Assert
        assertEquals(actual, 0.0, 0.001);
    }

    @Test(description = "getTax() should return exactly 6% of the subtotal")
    public void getTax_returnsCorrectTaxAmount() {
        // Arrange
        Mocking m = new Mocking();
        m.form.init();
        m.form.getCartItems().add(m.createBookWithPrice("100.00"));

        // Assert
        assertEquals(m.form.getTax(), 6.0,0.001);
    }

    // getTotal() tests

    @Test(description = "getTotal() should return 0.0 when the cart is empty")
    public void getTotal_emptyCartReturnsZero() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();
        
        // Assert
        assertEquals(m.form.getTotal(), 0.0, 0.001);
    }

    @Test(description = "getTotal() should return subtotal plus tax")
    public void getTotal_equalsSubtotalPlusTax() {
        // Arrange
        Mocking m = new Mocking();
        m.form.init();
        m.form.getCartItems().add(m.createBookWithPrice("100.00"));

        // Assert
        assertEquals(m.form.getTotal(), 106.0, 0.001);
    }


    @Test(description = "getTotal() should always equal getSubTotal() plus getTax() regardless of cart contents")
    public void getTotal_consistentWithSubtotalAndTax() {
        // Arrange
        Mocking m = new Mocking();
        m.form.init();
        m.form.getCartItems().add(m.createBookWithPrice("20.00"));
        m.form.getCartItems().add(m.createBookWithPrice("30.00"));

        // Act
        double expectedSubtotal = m.form.getSubTotal();
        double expectedTax = m.form.getTax();

        // Assert
        assertEquals(m.form.getTotal(), expectedSubtotal + expectedTax, 0.001);
    }

}