package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.form.InventoryForm;
import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.service.AuthorService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.component.UIViewRoot;
import javax.faces.application.FacesMessage;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Unit tests for {@link InventoryController}
 */
public class InventoryControllerTest {

    private static final class Mocking {

        @InjectMocks
        InventoryController controller;

        @Mock
        BookManager bookManager;

        @Mock
        AuthorManager authorManager;

        @Mock
        FacesContext facesContext;

        @Mock
        ExternalContext externalContext;

        @Mock
        AuthorService authorService;

        @Mock
        UIViewRoot viewRoot;

        @Spy
        InventoryForm inventoryForm;

        final Book selectedBook = new Book();

        final Author selectedAuthor = new Author();

        public Mocking() {
            openMocks(this);
            when(facesContext.getExternalContext()).thenReturn(externalContext);
            doNothing().when(inventoryForm).init();
            when(facesContext.getViewRoot()).thenReturn(viewRoot);
            when(viewRoot.getLocale()).thenReturn(Locale.ENGLISH);
            inventoryForm.setSelectedBook(selectedBook);
            inventoryForm.setSelectedAuthor(selectedAuthor);
        }
    }

    // newBook() test

    @Test(description = "newBook() should set a fresh Book instance on the form")
    public void newBook_setsNewBookOnForm() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.newBook();

        // Assert
        assertNotNull(m.inventoryForm.getSelectedBook());
    }

    // selectBook() test

    @Test(description = "selectBook() should set a deep clone of the book on the form, not the original reference")
    public void selectBook_setsClonedBookOnForm() {
        // Arrange
        Mocking m = new Mocking();
        Book original = new Book();
        original.setTitle("Prince of Thorns");

        // Act
        m.controller.selectBook(original);

        // Assert
        Book cloned = m.inventoryForm.getSelectedBook();
        assertNotSame(cloned, original);
        assertEquals(cloned.getTitle(), original.getTitle());
    }

    // saveBook() tests

    @Test(description = "saveBook() should persist the selected book via the book manager")
    public void saveBook_persistsSelectedBook() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.saveBook();

        // Verify
        verify(m.bookManager).update(m.selectedBook);
    }

    @Test(description = "saveBook() should refresh the inventory form after saving")
    public void saveBook_refreshesInventoryForm() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.saveBook();

        // Verify
        verify(m.inventoryForm).init();
    }

    // deactivateBook() tests

    @Test(description = "deactivateBook() should set the book's active flag to false")
    public void deactivateBook_setsBookInactive() {
        // Arrange
        Mocking m = new Mocking();
        Book book = new Book();

        // Act
        m.controller.deactivateBook(book);

        // Assert
        assertFalse(book.isActive());
    }

    @Test(description = "deactivateBook() should persist the updated book via the book manager")
    public void deactivateBook_persistsBook() {
        // Arrange
        Mocking m = new Mocking();
        Book book = new Book();

        // Act
        m.controller.deactivateBook(book);

        // Verify
        verify(m.bookManager).update(book);
    }

    @Test(description = "deactivateBook() should refresh the inventory form after deactivating")
    public void deactivateBook_refreshesInventoryForm() {
        // Arrange
        Mocking m = new Mocking();
        Book book = new Book();

        // Act
        m.controller.deactivateBook(book);

        // Verify
        verify(m.inventoryForm).init();
    }

    // activateBook() test - inactive author

    @Test(description = "activateBook() should not activate the book when its author is inactive")
    public void activateBook_doesNotActivateBookWhenAuthorIsInactive() {
        // Arrange
        Mocking m = new Mocking();
        Author inactiveAuthor = new Author();
        inactiveAuthor.setActive(false);
        Book book = new Book();
        book.setAuthor(inactiveAuthor);
        book.setActive(false);

        // Act
        m.controller.activateBook(book);

        // Assert
        assertFalse(book.isActive());

        // Verify
        verify(m.bookManager, never()).update(any());
        verify(m.facesContext).addMessage(eq(null), any(FacesMessage.class));
    }

    // activateBook() tests - active author

    @Test(description = "activateBook() should set the book's active flag to true when the author is active")
    public void activateBook_setsBookActiveWhenAuthorIsActive() {
        // Arrange
        Mocking m = new Mocking();
        Author activeAuthor = new Author();
        Book book = new Book();
        book.setAuthor(activeAuthor);
        book.setActive(false);

        // Act
        m.controller.activateBook(book);

        // Assert
        assertTrue(book.isActive());
    }

    @Test(description = "activateBook() should persist the book and refresh the form when the author is active")
    public void activateBook_persistsAndRefreshesWhenAuthorIsActive() {
        // Arrange
        Mocking m = new Mocking();
        Author activeAuthor = new Author();
        Book book = new Book();
        book.setAuthor(activeAuthor);

        // Act
        m.controller.activateBook(book);

        // Verify
        verify(m.bookManager).update(book);
        verify(m.inventoryForm).init();
    }

    // newAuthor() test

    @Test(description = "newAuthor() should set a fresh Author instance on the form")
    public void newAuthor_setsNewAuthorOnForm() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.newAuthor();

        // Assert
        assertNotNull(m.inventoryForm.getSelectedAuthor());
    }

    // selectAuthor() test

    @Test(description = "selectAuthor() should set a clone of the author on the form, not the original")
    public void selectAuthor_setsClonedAuthorOnForm() {
        // Arrange
        Mocking m = new Mocking();
        Author original = new Author();
        original.setName("Mark Lawrence");

        // Act
        m.controller.selectAuthor(original);

        // Assert
        Author cloned = m.inventoryForm.getSelectedAuthor();
        assertNotSame(cloned, original);
        assertEquals(cloned.getName(), original.getName());
    }

    // saveAuthor() tests

    @Test(description = "saveAuthor() should persist the selected author via the author manager")
    public void saveAuthor_persistsSelectedAuthor() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.saveAuthor();

        // Verify
        verify(m.authorManager).update(m.selectedAuthor);
    }

    @Test(description = "saveAuthor() should refresh the inventory form after saving")
    public void saveAuthor_refreshesInventoryForm() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.controller.saveAuthor();

        // Verify
        verify(m.inventoryForm).init();
    }

    // deactivateAuthor() tests

    @Test(description = "deactivateAuthor() should delegate to the author service")
    public void deactivateAuthor_delegatesToAuthorService() {
        // Arrange
        Mocking m = new Mocking();
        Author author = new Author();

        // Act
        m.controller.deactivateAuthor(author);

        // Verify
        verify(m.authorService).deactivateAuthor(author);
    }

    @Test(description = "deactivateAuthor() should refresh the inventory form after deactivating")
    public void deactivateAuthor_refreshesInventoryForm() {
        // Arrange
        Mocking m = new Mocking();
        Author author = new Author();

        // Act
        m.controller.deactivateAuthor(author);

        // Verify
        verify(m.inventoryForm).init();
    }

    // activateAuthor() tests

    @Test(description = "activateAuthor() should set the author's active flag to true")
    public void activateAuthor_setsAuthorActive() {
        // Arrange
        Mocking m = new Mocking();
        Author author = new Author();
        author.setActive(false);

        // Act
        m.controller.activateAuthor(author);

        // Assert
        assertTrue(author.isActive());
    }

    @Test(description = "activateAuthor() should persist the author and refresh the form")
    public void activateAuthor_persistsAndRefreshesForm() {
        // Arrange
        Mocking m = new Mocking();
        Author author = new Author();

        // Act
        m.controller.activateAuthor(author);

        // Verify
        verify(m.authorManager).update(author);
        verify(m.inventoryForm).init();
    }
}