package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.form.BookDetailForm;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.provider.DescriptionCache;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Unit tests for {@link BookDetailController}
 */
public class BookDetailControllerTest {

    private static final class Mocking {

        @InjectMocks
        BookDetailController controller;

        @Mock
        BookManager bookManager;

        @Mock
        BookDetailForm bookDetailForm;

        @Mock
        DescriptionCache descriptionCache;

        Book book = new Book();

        String isbn = "9780132350884";

        String cachedDescription = "A tale of a prince turned beggar turned legend.";

        public Mocking() {
            openMocks(this);
            when(bookDetailForm.getIsbn()).thenReturn(isbn);
            when(bookManager.findByIsbn(isbn)).thenReturn(book);
            when(descriptionCache.getDescription(isbn)).thenReturn(cachedDescription);
        }
    }

    // init() tests

    @Test(description = "init() should return early without calling the book manager when ISBN is null")
    public void init_returnsEarlyWhenIsbnIsNull() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookDetailForm.getIsbn()).thenReturn(null);

        // Act
        m.controller.init();

        // Verify
        verify(m.bookManager, never()).findByIsbn(any());
    }

    @Test(description = "init() should load the book from the manager using the ISBN from the form")
    public void init_loadsBooksByIsbn() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.init();

        // Verify
        verify(m.bookManager).findByIsbn(m.isbn);
        verify(m.bookDetailForm).setBook(m.book);
    }

    @Test(description = "init() should write the cached description to the form when the cache has one")
    public void init_setsDescriptionFromCacheWhenAvailable() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.init();

        // Verify
        verify(m.descriptionCache).getDescription(m.isbn);
        verify(m.bookDetailForm).setDescription(m.cachedDescription);
    }
}