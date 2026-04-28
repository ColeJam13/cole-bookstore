package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.provider.UserProvider;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CheckoutForm}
 */
public class CheckoutFormTest {

    private static final class Mocking {

        @InjectMocks
        CheckoutForm form;

        @Mock
        UserProvider userProvider;

        @Mock
        CustomerManager customerManager;

        final Customer existingCustomer = new Customer();

        public Mocking() {
            openMocks(this);
            when(userProvider.getUserName()).thenReturn(null);
        }
    }

    // init() tests - No user logged in

    @Test(description = "init() should create a fresh customer when no JAAS user is logged in")
    public void init_createsNewCustomerWhenNotLoggedIn() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getCustomer());
    }

    @Test(description = "init() should give the new customer a non-null shipping address when not logged in")
    public void init_newCustomerHasShippingAddress() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getCustomer().getShippingAddress());
    }

    @Test(description = "init() should give the new customer a non-null billing address when not logged in")
    public void init_newCustomerHasBillingAddress() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getCustomer().getBillingAddress());
    }

    // init() test - user is logged in

    @Test(description = "init() should loadthe existing customer from the manager when a user is logged in")
    public void init_loadsExistingCustomerWhenLoggedIn() {
        // Arrange
        Mocking m = new Mocking();
        String email = "testuser@example.com";
        when(m.userProvider.getUserName()).thenReturn(email);
        when(m.customerManager.findByEmail(email)).thenReturn(m.existingCustomer);

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getCustomer(), m.existingCustomer);

        // Verify
        verify(m.customerManager).findByEmail(email);
    }

    // sameAsShipping default state test

    @Test(description = "sameAsShipping should default to false on form initialization")
    public void init_sameAsShippingDefaultsFalse() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertFalse(m.form.isSameAsShipping());
    }

}