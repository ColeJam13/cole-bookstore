package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.jpa.Order;

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
 * Unit tests for the OrderForm
 */
public class OrderFormTest {

    @Mock
    private OrderManager orderManager;

    @InjectMocks
    private OrderForm orderForm;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // init() tests

    /**
     * Verifies that init() populates orderList with the list 
     * returned by orderManager.findAll()
     */
    @Test
    public void testInit_orderListIsPopulatedFromManager() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(orderManager.findAll()).thenReturn(orders);

        orderForm.init();

        Assert.assertEquals(orderForm.getOrderList(), orders,
            "orderList should be populated with the list returned by orderManager.findAll()");
    }

    /**
     * Verifies that init() handles an empty order list correctly
     * orderList should be an empty list, not null
     */
    @Test
    public void testInit_emptyListWhenNoOrders() {
        when(orderManager.findAll()).thenReturn(new ArrayList<>());

        orderForm.init();

        Assert.assertNotNull(orderForm.getOrderList(),
            "orderList should never be null even when no orders exist");
        Assert.assertEquals(orderForm.getOrderList().size(), 0,
            "orderList should be empty when no orders are returned");
    }
}
