package com.ksm.bookstore.form;

import com.ksm.bookstore.jpa.Book;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * Unit tests for the CartForm
 */
public class CartFormTest {
    
    private CartForm cartForm;

    /**
     * Runs before every @Test method, creates a fresh cart form
     * and manually calls init() to initialize cartItems list
     */
    @BeforeMethod
    public void setUp() {
        cartForm = new CartForm();
        cartForm.init();
    }

    /**
     * Helper method that creates a simple Book with only a price set.
     * Doesn't need other fields as we are only testing the math calculations
     * 
     * @param price the price to assign the book
     * @return a Book instance with a price
     */
    private Book createBookWithPrice(String price) {
        Book book = new Book();
        book.setPrice(new BigDecimal(price));
        return book;
    }

    // init() test

    /**
     * Verifies that init() produces a non-null and empty list
     * Cart should START empty, not null.
     */
    @Test
    public void testInit_cartStartsEmpty() {
        Assert.assertNotNull(cartForm.getCartItems(),
            "Cart items list should not be null after init");
        Assert.assertEquals(cartForm.getCartItems().size(), 0,
            "Cart should contain zero items after init");
    }

    // getSubTotal() tests

    /**
     * Verifies getSubTotal() returns 0.0 when cart is empty
     */
    @Test
    public void testGetSubtotal_emptyCartReturnsZero() {
        Assert.assertEquals(cartForm.getSubTotal(), 0.0, 0.001,
            "Subtotal of an empty cart should be 0.0");
    }

    /**
     * Verifies that getSubTotal() correctly sums the prices of all
     * books in the cart with multiple items
     */
    @Test
    public void testGetSubTotal_returnsCorrectSumForMultipleBooks() {
        cartForm.getCartItems().add(createBookWithPrice("10.00"));
        cartForm.getCartItems().add(createBookWithPrice("15.50"));
        cartForm.getCartItems().add(createBookWithPrice("9.99"));

        // 10.00 + 15.50 + 9.99 = 35.49
        Assert.assertEquals(cartForm.getSubTotal(), 35.49, 0.001,
            "Subtotal should equal the sum of all book prices");
    }

    /**
     * Verifies that getSubTotal() works correctly with a single item
     * should just return the price of that single book
     */
    @Test
    public void testGetSubTotal_singleBookReturnsItsPrice() {
        cartForm.getCartItems().add(createBookWithPrice("24.99"));

        Assert.assertEquals(cartForm.getSubTotal(), 24.99, 0.001,
            "Subtotal with one book should equal that book's price");
    }

    // getTax() tests

    /**
     * Verifies that getTax() returns 0.0 on an empty cart
     */
    @Test
    public void testGetTax_emptyCartReturnsZero() {
        Assert.assertEquals(cartForm.getTax(), 0.0, 0.001,
            "Tax on an empty cart should be 0.0");
    }

    /**
     * Verifies that getTax() correctly calculates 6% of the subtotal
     */
    @Test
    public void testGetTax_returnsCorrectTaxAmount() {
        cartForm.getCartItems().add(createBookWithPrice("100.00"));

        // 6% of 100.00 = 6.00
        Assert.assertEquals(cartForm.getTax(), 6.0, 0.001,
            "Tax should be 6% of the subtotal");
    }

    // getTotal() tests

    /**
     * Verifies that getTotal() returns 0.0 when cart is empty
     */
    @Test
    public void testGetTotal_emptyCartReturnsZero() {
        Assert.assertEquals(cartForm.getTotal(), 0.0, 0.001,
            "Total of an empty cart should be 0.0");
    }

    /**
     * Verifies that getTotal() returns subtotal plus tax
     */
    @Test
    public void testGetTotal_equalsSubtotalPlusTax() {
        cartForm.getCartItems().add(createBookWithPrice("100.00"));

        Assert.assertEquals(cartForm.getTotal(), 106.0, 0.001,
            "Total should equal subtotal plus tax");
    }

    /**
     * Verifies that getTotal() stays consistent with getSubTotal() and
     * getTax() across multiple items
     */
    @Test
    public void testGetTotal_consistentWithSubtotalAndTax() {
        cartForm.getCartItems().add(createBookWithPrice("20.00"));
        cartForm.getCartItems().add(createBookWithPrice("30.00"));

        double expectedSubtotal = cartForm.getSubTotal();
        double expectedTax = cartForm.getTax();

        Assert.assertEquals(cartForm.getTotal(), expectedSubtotal + expectedTax, 0.001,
            "Total should always equal subtotal plus tax");
    }
}
