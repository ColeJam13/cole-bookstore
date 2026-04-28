package com.ksm.bookstore.form;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link InventoryForm}
 */
public class InventoryFormTest {

    private static final class Mocking {

        @InjectMocks
        InventoryForm form;

        @Mock
        BookManager bookManager;

        @Mock
        AuthorManager authorManager;

        final List<Book> books = List.of(createBook("Book One"), createBook("Book Two"));

        final Author activeAuthorA = createAuthor("Mark Lawrence", true);

        final Author activeAuthorB = createAuthor("Eoin Colfer", true);

        final Author inactiveAuthor = createAuthor("Suzanne Collins", false);

        List<Author> allAuthors = List.of(activeAuthorA, activeAuthorB, inactiveAuthor);

        public Mocking() {
            openMocks(this);
            when(bookManager.findAll()).thenReturn(books);
            when(authorManager.findAll()).thenReturn(allAuthors);
        }

        // Helper method to create a Book with only a title: other fields not needed
        public Book createBook(String title) {
            Book book = new Book();
            book.setTitle(title);
            return book;
        }

        // Helper method to create an Author with a name and status
        public Author createAuthor(String name, boolean active) {
            Author author = new Author();
            author.setName(name);
            author.setActive(active);
            return author;
        }
    }

    // init() tests

    @Test(description = "init() should populate bookList with all books returned by the book manager")
    public void init_bookListPopulatedFromManager() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getBookList(), m.books);
        
        // Verify
        verify(m.bookManager).findAll();
    }

    @Test(description = "init() should populate authorList with all authors returned by the author manager")
    public void init_authorListPopulatedFromManager() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getAuthorList(), m.allAuthors);

        // Verify
        verify(m.authorManager).findAll();
    }

    @Test(description = "init() should set selectedBook to a fresh non-null Book")
    public void init_selectedBookInitializedAsNewBook() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getSelectedBook());
    }

    @Test(description = "init() should set selectedAuthor to a fresh non-null Author")
    public void init_selectedAuthorInitializedAsNewAuthor() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertNotNull(m.form.getSelectedAuthor());
    }

    @Test(description = "init() should populate activeAuthorList with only authors who are active")
    public void init_activeAuthorListContainsOnlyActiveAuthors() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.form.init();

        // Assert
        assertEquals(m.form.getActiveAuthorList().size(), 2);
        assertTrue(m.form.getActiveAuthorList().contains(m.activeAuthorA));
        assertTrue(m.form.getActiveAuthorList().contains(m.activeAuthorB));
    }

    @Test(description = "init() should produce an empty activeAuthorList when no authors are active")
    public void init_activeAuthorListEmptyWhenNoActiveAuthors() {
        // Arrange
        Mocking m = new Mocking();
        Author inactiveA = m.createAuthor("Inactive One", false);
        Author inactiveB = m.createAuthor("Inactive Two", false);
        when(m.authorManager.findAll()).thenReturn(List.of(inactiveA, inactiveB));

        // Act
        m.form.init();

        // Assert
        assertTrue(m.form.getActiveAuthorList().isEmpty());
    }

    // completeAuthor() tests

    @Test(description = "completeAuthor() should return authors whose names contain the query string")
    public void completeAuthor_returnsMatchingAuthors() {
        // Arrange
        Mocking m = new Mocking();
        m.form.init();

        // Act
        List<Author> result = m.form.completeAuthor("Mark");

        // Assert
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getName(), "Mark Lawrence");
    }

    @Test(description = "completeAuthor() should be case-insensitive when matching author names")
    public void completeAuthor_isCaseInsensitive() {
        // Arrange
        Mocking m = new Mocking();
        m.form.init();

        // Act
        List<Author> result = m.form.completeAuthor("mark");

        // Assert
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getName(), "Mark Lawrence");
    }

    @Test(description = "completeAuthor() should return an empty list when no authors match the query")
    public void completeAuthor_returnsEmptyListWhenNoMatch() {
        // Arrange
        Mocking m = new Mocking();
        m.form.init();

        // Act
        List<Author> result = m.form.completeAuthor("BingusBongus");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test(description = "completeAuthor() should return results sorted alphabetically")
    public void completeAuthor_resultsSortedAlphabetically() {
        // Arrange
        Mocking m = new Mocking();
        Author authorZ = m.createAuthor("Zora Neale", true);
        Author authorA = m.createAuthor("Aaron Blake", true);
        when(m.authorManager.findAll()).thenReturn(List.of(authorZ, authorA));
        m.form.init();

        // Act
        List<Author> result = m.form.completeAuthor("a");

        // Assert
        assertEquals(result.get(0).getName(), "Aaron Blake");
        assertEquals(result.get(1).getName(), "Zora Neale");
    }
}