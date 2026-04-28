package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.BookSearchForm;
import com.ksm.bookstore.jpa.Book;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Unit tests for {@link HomeController}
 */
public class HomeControllerTest {

    private static final class Mocking {

        @InjectMocks
        HomeController controller;

        @Mock
        FacesContext facesContext;

        @Mock
        ExternalContext externalContext;

        @Spy
        BookSearchForm bookSearchForm;

        final Book selectedBook = new Book();

        final String isbn = "9780132350884";

        public Mocking() {
            openMocks(this);
            when(facesContext.getExternalContext()).thenReturn(externalContext);
            selectedBook.setIsbn(isbn);
            bookSearchForm.setSelectedBook(selectedBook);
        }
    }

    // navigate() test

    @Test(description = "navigate() should redirect to the book detail page with the selected book's ISBN in the URL")
    public void navigate_redirectsToBookDetailPageWithIsbn() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.navigate();

        // Verify
        verify(m.externalContext).redirect("book-detail.jsf?isbn=9780132350884");
        }
    }
