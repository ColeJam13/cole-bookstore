package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Book;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.DataProvider;
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
 * Unit tests for {@link BookSearchForm}
 */
public class BookSearchFormTest {

    private static final class Mocking {

        @InjectMocks
        BookSearchForm form;

        @Mock
        BookManager bookManager;

        final List<Book> activeBooks = List.of(createBook("Book 1"), createBook("Book 2"));

        public Mocking() {
            openMocks(this);
            when(bookManager.findAllActiveBooks()).thenReturn(activeBooks);
        }

        // Helper method to create Book with a title: other fields are blank since theyre not needed
        public Book createBook(String title) {
            Book book = new Book();
            book.setTitle(title);
            return book;
        }
    }

    @DataProvider(name = "activeBookListProvider")
    public Object[][] activeBookListProvider() {
        Mocking m = new Mocking();
        return new Object[][] {
            { List.of(m.createBook("Book A"), m.createBook("Book B")) },
            { List.of(m.createBook("Book C")) },
            { new ArrayList<>() }
        };
    }

    // init() tests

    @Test(description = "init() should populate searchResults with the active books returned by the manager")
    public void init_searchResultsPopulatedFromManager() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getSearchResults(), m.activeBooks);

        // Verify
        verify(m.bookManager).findAllActiveBooks();
    }

    @Test(description = "init() should correctly handle various list sizes of active books",
            dataProvider = "activeBookListProvider")
    public void init_variousActiveBookLists(List<Book> books) {
        //Arrange
        Mocking m = new Mocking();
        when(m.bookManager.findAllActiveBooks()).thenReturn(books);

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getSearchResults(), books);
    }

    @Test(description = "init() should set searchResults to a non-null empty list when no active books exist")
    public void init_emptyListWhenNoBooksAreActive() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookManager.findAllActiveBooks()).thenReturn(new ArrayList<>());

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getSearchResults());
        assertTrue(m.form.getSearchResults().isEmpty());
    }

    @Test(description = "init() should propagate exceptions thrown by the book manager",
        expectedExceptions = RuntimeException.class)
    public void init_propagatesExceptionFromManager() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookManager.findAllActiveBooks()).thenThrow(new RuntimeException("Database error"));

        // Act
        m.form.init();
    }
}