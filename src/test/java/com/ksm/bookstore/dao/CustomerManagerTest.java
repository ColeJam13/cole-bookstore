package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Customer;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Unit tests for {@link CustomerManager}
 */
public class CustomerManagerTest {

    private static final class Mocking {

        @InjectMocks
        CustomerManager customerManager;

        @Mock
        EntityManager entityManager;

        @Mock
        TypedQuery<Customer> customerQuery;

        Customer customer = createCustomer("jane@example.com");

        public Mocking() {
            openMocks(this);
            when(entityManager.createQuery(anyString(), eq(Customer.class))).thenReturn(customerQuery);
            when(customerQuery.setParameter(anyString(), any())).thenReturn(customerQuery);
            when(customerQuery.getSingleResult()).thenReturn(customer);
        }

        // Helper method that creates a customer with an email address
        private Customer createCustomer(String email) {
            Customer c = new Customer();
            c.setEmail(email);
            return c;
        }
    }

    // findByEmail tests

    @Test(description = "findByEmail() should return the matching customer when found")
    public void findByEmail_returnsCustomerWhenFound() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        Customer result = m.customerManager.findByEmail("jane@example.com");

        // Assert
        assertEquals(result, m.customer);
    }

    @Test(description = "findByEmail() should return null when no customer matches the given email")
    public void findByEmail_returnsNullWhenNotFound() {
        // Arrange
        Mocking m = new Mocking();
        when(m.customerQuery.getSingleResult()).thenThrow(new NoResultException());

        // Act
        Customer result = m.customerManager.findByEmail("nobody@example.com");

        // Assert
        assertNull(result);
    }
}