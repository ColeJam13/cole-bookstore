package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.provider.UserProvider;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link ProfileForm}
 */
public class ProfileFormTest {

    private static final class Mocking {

        @InjectMocks
        ProfileForm form;

        @Mock
        UserProvider userProvider;

        @Mock
        CustomerManager customerManager;

        @Mock
        OrderManager orderManager;

        final String email = "testuser@example.com";

        final Customer existingCustomer = new Customer();

        final List<Order> orderHistory = List.of(new Order(), new Order());

        public Mocking() {
            openMocks(this);
            when(userProvider.getUserName()).thenReturn(email);
            when(customerManager.findByEmail(email)).thenReturn(existingCustomer);
            when(orderManager.findByCustomer(existingCustomer)).thenReturn(orderHistory);
        }
    }

    // init() tests - existing customer

    @Test(description = "init() should load the existing customer when customerManager finds one by email")
    public void init_loadsExistingCustomerWhenFound() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getCustomer(), m.existingCustomer);

        // Verify
        verify(m.customerManager).findByEmail(m.email);
    }

    @Test(description = "init() should populate orderHistory with the orders returned by the order manager")
    public void init_orderHistoryPopulatedFromManager() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getOrderHistory(), m.orderHistory);

        // Verify
        verify(m.orderManager).findByCustomer(m.existingCustomer);
    }

    @Test(description = "init() should set orderHistory to an empty list when the customer has no orders")
    public void init_orderHistoryEmptyWhenNoOrders() {
        // Arrange
        Mocking m = new Mocking();
        when(m.orderManager.findByCustomer(m.existingCustomer)).thenReturn(new ArrayList<>());

        // Act
        m.form.init();

        // Assert
        assertTrue(m.form.getOrderHistory().isEmpty());
    }

    // init() tests - new customer

    @Test(description = "init() should create a new customer when no existing customer is found by email")
    public void init_createsNewCustomerWhenNotFound() {
        // Arrange
        Mocking m = new Mocking();
        when(m.customerManager.findByEmail(m.email)).thenReturn(null);
        when(m.orderManager.findByCustomer(null)).thenReturn(new ArrayList<>());

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getCustomer());
    }

    @Test(description = "init() should pre-populate the new customer's email from the JAAS username")
    public void init_newCustomerEmailPrePopulated() {
        // Arrange
        Mocking m = new Mocking();
        when(m.customerManager.findByEmail(m.email)).thenReturn(null);
        when(m.orderManager.findByCustomer(null)).thenReturn(new ArrayList<>());

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getCustomer().getEmail(), m.email);
    }

    @Test(description = "init() should give the new customer a non-null shipping address")
    public void init_newCustomerHasShippingAddress() {
        // Arrange
        Mocking m = new Mocking();
        when(m.customerManager.findByEmail(m.email)).thenReturn(null);
        when(m.orderManager.findByCustomer(null)).thenReturn(new ArrayList<>());

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getCustomer().getShippingAddress());
    }

    @Test(description = "init() should give the new customer a non-null billing address")
    public void init_newCustomerHasBillingAddress() {
        // Arrange
        Mocking m = new Mocking();
        when(m.customerManager.findByEmail(m.email)).thenReturn(null);
        when(m.orderManager.findByCustomer(null)).thenReturn(new ArrayList<>());

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getCustomer().getBillingAddress());
    }
}