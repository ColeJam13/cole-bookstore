package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.dao.OrderManager;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

/**
 * Unit tests for the DashboardForm
 */
public class DashboardFormTest {

    @Mock
    private BookManager bookManager;

    @Mock
    private OrderManager orderManager;

    @Mock
    private AuthorManager authorManager;

    @InjectMocks
    private DashboardForm dashboardForm;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // init() tests

    /**
     * Verifies that init() stores the value returned by bookManager.count()
     * into the totalBooks field on the dashboard
     */
    @Test
    public void testInit_totalBooksPopulatedFromManager() {
        when(bookManager.count()).thenReturn(5L);
        when(orderManager.count()).thenReturn(0L);
        when(authorManager.count()).thenReturn(0L);

        dashboardForm.init();

        Assert.assertEquals(dashboardForm.getTotalBooks(), 5L,
            "totalBooks should reflect the count returned by bookManager.count()");
    }
    
    /**
     * Verifies that init() stores the value returned by orderManager.count() into
     * the totalOrders field on the dashboard
     */
    @Test
    public void testInit_totalOrdersPopulatedFromManager() {
        when(bookManager.count()).thenReturn(0L);
        when(orderManager.count()).thenReturn(12L);
        when(authorManager.count()).thenReturn(0L);

        dashboardForm.init();

        Assert.assertEquals(dashboardForm.getTotalOrders(), 12L,
            "totalOrders should reflect the count returned by orderManager.count()");
    }

    /**
     * Verifies that init() stores the value returned by authorManager.count()
     * into the totalAuthors field on the dashboard
     */
    @Test
    public void testInit_totalAuthorsPopulatedFromManager() {
        when(bookManager.count()).thenReturn(0L);
        when(orderManager.count()).thenReturn(0L);
        when(authorManager.count()).thenReturn(3L);

        dashboardForm.init();

        Assert.assertEquals(dashboardForm.getTotalAuthors(), 3L,
            "totalAuthors should reflect the count returned by authorManager.count()");
    }

    /**
     * Verifies that all three counts are populated correctly in a single
     * init() call - confirms that they don't interfere with each other
     */
    @Test
    public void testInit_allCountsPopulatedTogether() {
        when(bookManager.count()).thenReturn(10L);
        when(orderManager.count()).thenReturn(25L);
        when(authorManager.count()).thenReturn(4L);

        dashboardForm.init();

        Assert.assertEquals(dashboardForm.getTotalBooks(), 10L,
            "totalBooks should be 10");
        Assert.assertEquals(dashboardForm.getTotalOrders(), 25L,
            "totalOrders should be 25");
        Assert.assertEquals(dashboardForm.getTotalAuthors(), 4L,
            "totalAuthors should be 4");
    }
}
