package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.jpa.Address;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.provider.UserProvider;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

/**
 * Unit tests for the CheckoutForm: covers two branches
 * 1) a JAAS user is logged in
 * 2) no user is logged in
 */
public class CheckoutFormTest {

    @Mock
    private UserProvider userProvider;

    @Mock
    private CustomerManager customerManager;

    @InjectMocks
    private CheckoutForm checkoutForm;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    // init() tests

    /**
     * Verifies that when a JAAS user if logged in, init() uses their username (email) to look up an
     * existing Customer record and stores it in the form. No new customer is created.
     */
    @Test
    public void testInit_existingCustomerLoadedWhenUserLoggedIn() {
        String email = "jane.test@example.com";
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail(email);

        when(userProvider.getUserName()).thenReturn(email);
        when(customerManager.findByEmail(email)).thenReturn(existingCustomer);

        checkoutForm.init();

        Assert.assertEquals(checkoutForm.getCustomer(), existingCustomer,
            "init() should load the existing customer when a user is logged in");
    }

    /**
     * Verifies the fallback branch: when no user is logged in, getUserName() returns null
     * the Optional short-circuits, and createCustomer() is called instead which will create a
     * fresh customer with empty information fields
     */
    @Test
    public void testInit_freshCustomerCreatedWhenNoUserLoggedIn() {
        when(userProvider.getUserName()).thenReturn(null);

        checkoutForm.init();

        Assert.assertNotNull(checkoutForm.getCustomer(),
            "init() should create a fresh customer when no user is logged in");
        Assert.assertNotNull(checkoutForm.getCustomer().getShippingAddress(),
            "Shipping address should be initialized on a fresh customer");
        Assert.assertNotNull(checkoutForm.getCustomer().getBillingAddress(),
            "Billing address should be initialized on a fresh customer");
    }

    /**
     * Verifies that when a user is logged in but their email does not exist in the database
     * customerManager.findByEmail() returns null and the Optional chain falls through to 
     * createCustomer() as a fallback. Covers edge case of JAAS user having no matching customer record
     */
    @Test
    public void testInit_freshCustomerCreatedWhenEmailNotFound() {
        String email = "unknown@example.com";

        when(userProvider.getUserName()).thenReturn(email);
        when(customerManager.findByEmail(email)).thenReturn(null);

        checkoutForm.init();

        Assert.assertNotNull(checkoutForm.getCustomer(),
            "init() should fall back to a fresh customer when email is not found");
        Assert.assertNotNull(checkoutForm.getCustomer().getShippingAddress(),
            "Shipping address should be initialized on the fallback customer");
        Assert.assertNotNull(checkoutForm.getCustomer().getBillingAddress(),
            "Billing address should be initialized on the fallback customer");
    }
}
