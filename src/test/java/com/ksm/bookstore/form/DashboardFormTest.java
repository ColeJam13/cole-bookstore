package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.dao.OrderManager;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link DashboardForm}
 */
public class DashboardFormTest {

    private static final class Mocking {

        @InjectMocks
        DashboardForm form;

        @Mock
        BookManager bookManager;

        @Mock
        OrderManager orderManager;

        @Mock
        AuthorManager authorManager;

        public Mocking() {
            openMocks(this);
            when(bookManager.count()).thenReturn(10L);
            when(orderManager.count()).thenReturn(20L);
            when(authorManager.count()).thenReturn(30L);
        }
    }

    // init() tests

    @Test(description = "init() should set totalBooks to the count returned by the book manager")
    public void init_totalBooksSetFromManager() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getTotalBooks(), 10L);

        // Verify
        verify(m.bookManager).count();
    }

    @Test(description = "init() should set totalOrders to the count returned by the order manager")
    public void init_totalOrdersSetFromManager() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getTotalOrders(), 20L);

        // Verify
        verify(m.orderManager).count();
    }

    @Test(description = "init() should set totalAuthors to the count returned by author manager")
    public void init_totalAuthorsSetFromManager() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getTotalAuthors(), 30L);

        // Verify
        verify(m.authorManager).count();
    }

    @Test(description = "init() should set all three totals correctly in a single call")
    public void init_allTotalsSetCorrectly() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getTotalBooks(), 10L);
        assertEquals(m.form.getTotalOrders(), 20L);
        assertEquals(m.form.getTotalAuthors(), 30L);
    }
}