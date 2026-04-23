package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.provider.UserProvider;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Unit tests for the ProfileForm. Covers two branches:
 * 1) A customer record exists for the logged-in users email: load it.
 * 2) No customer record exists: createCustomer() is called as a fallback,
 * prepopulates the email field from the JAAS username
 */
public class ProfileFormTest {

    @Mock
    private UserProvider userProvider;

    @Mock
    private CustomerManager customerManager;

    @Mock
    private OrderManager orderManager;

    @InjectMocks
    private ProfileForm profileForm;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // init() tests

    /**
     * Verifies that when a customer record exists for the logged-in user's email
     * init() loads that customer and stores it on the form
     */
    @Test
    public void testInit_existingCustomerLoadedWhenFound() {
        String email = "jane.test@example.com";
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail(email);

        when(userProvider.getUserName()).thenReturn(email);
        when(customerManager.findByEmail(email)).thenReturn(existingCustomer);
        when(orderManager.findByCustomer(existingCustomer)).thenReturn(new ArrayList<>());

        profileForm.init();

        Assert.assertEquals(profileForm.getCustomer(), existingCustomer,
            "init() should load the existing customer when one is found by email");
    }

    /**
     * Verifies the fallback branch: when no customer exists for the logged-in user's email, createCustomer()
     * is called which builds a fresh Customer with the email pre-populated from the JAAS username
     */
    @Test
    public void testInit_freshCustomerCreatedWhenNotFound() {
        String email = "newuser@example.com";

        when(userProvider.getUserName()).thenReturn(email);
        when(customerManager.findByEmail(email)).thenReturn(null);
        when(orderManager.findByCustomer(null)).thenReturn(new ArrayList<>());

        profileForm.init();

        Assert.assertNotNull(profileForm.getCustomer(),
            "init() should create a fresh customer when none is found");
        Assert.assertEquals(profileForm.getCustomer().getEmail(), email,
            "The fresh customer's email should be pre-populated from the JAAS username");
        Assert.assertNotNull(profileForm.getCustomer().getShippingAddress(),
            "Shipping address should be initialized on a fresh customer");
        Assert.assertNotNull(profileForm.getCustomer().getBillingAddress(),
            "Billing address should be initialized on a fresh customer");
    }
    
    /**
     * Verifies that init() always loads the order history for the customer
     * regardless of if they were found or newly created
     */
    @Test
    public void testInit_orderHistoryIsLoaded() {
        String email = "jane.test@example.com";
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail(email);

        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(userProvider.getUserName()).thenReturn(email);
        when(customerManager.findByEmail(email)).thenReturn(existingCustomer);
        when(orderManager.findByCustomer(existingCustomer)).thenReturn(orders);

        profileForm.init();

        Assert.assertEquals(profileForm.getOrderHistory(), orders,
            "orderHistory should be populated with the customer's orders after init()");
    }

    /**
     * Verifies that init() handles a customer with no order history correctly
     * orderHistory should be an empty list, not null
     */
    @Test
    public void testInit_emptyOrderHistoryWhenNoOrders() {
        String email = "jane.test@example.com";
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail(email);

        when(userProvider.getUserName()).thenReturn(email);
        when(customerManager.findByEmail(email)).thenReturn(existingCustomer);
        when(orderManager.findByCustomer(existingCustomer)).thenReturn(new ArrayList<>());

        profileForm.init();

        Assert.assertNotNull(profileForm.getOrderHistory(),
            "orderHistory should never be null even when the customer has no orders");
        Assert.assertEquals(profileForm.getOrderHistory().size(), 0,
            "orderHistory should be empty when no orders exist for this customer");
    }
}
