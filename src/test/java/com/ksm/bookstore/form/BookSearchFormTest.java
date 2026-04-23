package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Book;

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
 * Unit tests for the BookSearchForm
 */
public class BookSearchFormTest {
    
    @Mock
    private BookManager bookManager;

    @InjectMocks
    private BookSearchForm bookSearchForm;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // init() tests

    /**
     * Verifies that init() stores the list returned by bookManager.findAllActiveBooks()
     * into the searchResults field
     */
    @Test
    public void testInit_searchResultsPopulatedFromManager() {
        List<Book> activeBooks = new ArrayList<>();
        activeBooks.add(new Book());
        activeBooks.add(new Book());

        when(bookManager.findAllActiveBooks()).thenReturn(activeBooks);

        bookSearchForm.init();

        Assert.assertEquals(bookSearchForm.getSearchResults(), activeBooks,
            "searchResults should be populated with the list returned by findAllActiveBooks()");
    }

    /**
     * Verifies that init() still works correctly when there are no active books
     * searchResults should be an empty list, not null
     */
    @Test
    public void testInit_emptyListWhenNoBooksActive() {
        when(bookManager.findAllActiveBooks()).thenReturn(new ArrayList<>());

        bookSearchForm.init();

        Assert.assertNotNull(bookSearchForm.getSearchResults(),
            "searchResults should never be null even with no active books");
        Assert.assertEquals(bookSearchForm.getSearchResults().size(), 0,
            "searchResults should be empty when no active books are returned");
    }
}
