package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.form.ProfileForm;
import com.ksm.bookstore.jpa.Customer;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Unit tests for {@link ProfileController}
 */
public class ProfileControllerTest {

    private static final class Mocking {

        @InjectMocks
        ProfileController controller;

        @Mock
        CustomerManager customerManager;

        @Mock
        ProfileForm profileForm;

        Customer customer = new Customer();

        public Mocking() {
            openMocks(this);
            when(profileForm.getCustomer()).thenReturn(customer);
        }
    }

    // saveProfile() test

    @Test(description = "saveProfile() should persist the customer from the form via the customer manager")
    public void saveProfile_persistsCustomerFromForm() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.saveProfile();

        // Verify
        verify(m.customerManager).update(m.customer);
    }
}