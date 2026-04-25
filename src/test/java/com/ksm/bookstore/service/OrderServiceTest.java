package com.ksm.bookstore.service;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.dao.OrderItemManager;
import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.form.CheckoutForm;
import com.ksm.bookstore.jpa.Address;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.jpa.Customer;
import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.jpa.OrderItem;
import com.ksm.bookstore.model.OrderStatus;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link OrderService}
 */
public class OrderServiceTest {

    private static final class Mocking {

        @InjectMocks
        OrderService orderService;

        @Mock
        OrderManager orderManager;

        @Mock
        CustomerManager customerManager;

        @Mock
        OrderItemManager orderItemManager;

        @Mock
        CheckoutForm checkoutForm;

        Customer customer = new Customer();

        Order savedOrder = mock(Order.class);

        Book bookOne = createBook(new BigDecimal("10.00"));
        Book bookTwo = createBook(new BigDecimal("15.00"));

        List<Book> cartItems = List.of(bookOne, bookTwo);

        public Mocking() {
            openMocks(this);
            when(checkoutForm.isSameAsShipping()).thenReturn(false);
            when(checkoutForm.getCustomer()).thenReturn(customer);
            when(customerManager.update(customer)).thenReturn(customer);
            when(orderManager.update(any(Order.class))).thenReturn(savedOrder);
            when(savedOrder.getOrderNumber()).thenReturn(100L);
        }

        // Helper method to build a Book with just a set price
        private Book createBook(BigDecimal price) {
            Book book = new Book();
            book.setPrice(price);
            return book;
        }
    }

    // submitOrder() tests

    @Test(description = "submitOrder() should return the order number from the saved order")
    public void submitOrder_returnsOrderNumber() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        Long result = m.orderService.submitOrder(m.cartItems);

        // Assert
        assertEquals(result, Long.valueOf(100L));
    }

    @Test(description = "submitOrder() should calculate the order total as the sum of all book prices")
    public void submitOrder_calculatesOrderTotalCorrectly() {
        // Arrange
        Mocking m = new Mocking();
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        // Act
        m.orderService.submitOrder(m.cartItems);
        
        // Verify
        verify(m.orderManager).update(orderCaptor.capture());

        // Assert
        assertEquals(orderCaptor.getValue().getOrderTotal(), new BigDecimal("25.00"));
    }

    @Test(description = "submitOrder() should set the order status to SUBMITTED before saving")
    public void submitOrder_setsStatusToSubmitted() {
        // Arrange
        Mocking m = new Mocking();
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        // Act
        m.orderService.submitOrder(m.cartItems);
        
        // Verify
        verify(m.orderManager).update(orderCaptor.capture());

        // Assert
        assertEquals(orderCaptor.getValue().getOrderStatus(), OrderStatus.SUBMITTED);
    }

    @Test(description = "submitOrder() should persist the customer thorugh customerManager")
    public void submitOrder_savesCustomer() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.orderService.submitOrder(m.cartItems);

        // Verify
        verify(m.customerManager).update(m.customer);
    }

    @Test(description = "submitOrder() should create and save one OrderItem per book in the cart")
    public void submitOrder_createsOneOrderItemPerBook() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.orderService.submitOrder(m.cartItems);

        // Verify
        verify(m.orderItemManager, times(2)).update(any(OrderItem.class));
    }

    @Test(description = "submitOrder() should produce a zero order total when the cart is empty")
    public void submitOrder_producesZeroTotalForEmptyCart() {
        // Arrange
        Mocking m = new Mocking();
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        // Act
        m.orderService.submitOrder(List.of());
        
        // Verify
        verify(m.orderManager).update(orderCaptor.capture());

        // Assert
        assertEquals(orderCaptor.getValue().getOrderTotal(), BigDecimal.ZERO);
    }

    @Test(description = "submitOrder() should not call orderItemManager.update() when the cart is empty")
    public void submitOrder_doesNotCreateOrderItemsForEmptyCart() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.orderService.submitOrder(List.of());

        // Verify
        verify(m.orderItemManager, times(0)).update(any(OrderItem.class));
    }

    @Test(description = "submitOrder() should copy the shipping address to billing when sameAsShipping is true")
    public void submitOrder_copiesBillingFromShippingWhenSameAsShipping() {
        // Arrange
        Mocking m = new Mocking();
        Address shippingAddress = new Address();
        m.customer.setShippingAddress(shippingAddress);
        when(m.checkoutForm.isSameAsShipping()).thenReturn(true);

        // Act
        m.orderService.submitOrder(m.cartItems);

        // Assert
        assertSame(m.customer.getBillingAddress(), shippingAddress);
    }

    @Test(description = "submitOrder() should not modify the billing address when sameAsShipping is false")
    public void submitOrder_doesNotOverrideBillingWhenNotSameAsShipping() {
        // Arrange
        Mocking m = new Mocking();
        Address shippingAddress = new Address();
        Address billingAddress = new Address();
        m.customer.setShippingAddress(shippingAddress);
        m.customer.setBillingAddress(billingAddress);

        // Act
        m.orderService.submitOrder(m.cartItems);

        // Assert
        assertSame(m.customer.getBillingAddress(), billingAddress);
    }
}