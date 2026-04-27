package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.BookSearchForm;
import com.ksm.bookstore.jpa.Book;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
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
        BookSearchForm bookSearchForm;

        Book selectedBook = new Book();

        String isbn = "9780132350884";

        public Mocking() {
            openMocks(this);
            selectedBook.setIsbn(isbn);
            when(bookSearchForm.getSelectedBook()).thenReturn(selectedBook);
        }
    }

    // navigate() test

    @Test(description = "navigate() should redirect to the book detail page with the selected book's ISBN in the URL")
    public void navigate_redirectsToBookDetailPageWithIsbn() throws IOException {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<FacesContext> mockedFaces = mockStatic(FacesContext.class)) {
            FacesContext facesContextMock = mock(FacesContext.class);
            ExternalContext externalContextMock = mock(ExternalContext.class);
            mockedFaces.when(FacesContext::getCurrentInstance).thenReturn(facesContextMock);
            when(facesContextMock.getExternalContext()).thenReturn(externalContextMock);

            // Act
            m.controller.navigate();

            // Verify
            verify(externalContextMock).redirect("book-detail.jsf?isbn=" + m.isbn);
        }
    }
}