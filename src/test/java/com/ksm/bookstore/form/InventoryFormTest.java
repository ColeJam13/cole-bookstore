package com.ksm.bookstore.form;

import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Author;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

/**
 * Unit tests for InventoryForm
 */
public class InventoryFormTest {

    @Mock
    private BookManager bookManager;

    @Mock
    private AuthorManager authorManager;

    @InjectMocks
    private InventoryForm inventoryForm;

    /**
     * Runs before every @Test method, creates a new inventory
     * form and calls init() to initialize the fields
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper Methods

    /**
     * Creates an Author with a given name and active status
     * @param name the authors name
     * @param active whether the author is active
     * @return a populated Author instance
     */
    private Author createAuthor(String name, boolean active) {
        Author author = new Author();
        author.setName(name);
        author.setActive(active);
        return author;
    }

    /**
     * Creates a Book with a given title
     * @param title the book's title
     * @return a populated Book instance
     */
    private Book createBook(String title) {
        Book book = new Book();
        book.setTitle(title);
        return book;
    }

    /**
     * Method used in tests that stubs both managers with empty list
     * which allows init to properlly run without exceptions
     */
    private void stubManagersWithEmptyLists() {
        when(bookManager.findAll()).thenReturn(new ArrayList<>());
        when(authorManager.findAll()).thenReturn(new ArrayList<>());
    }

    // init() tests

    /**
     * Verifies that BookManager actually populates the list of books
     */
    @Test
    public void testInit_bookListIsPopulatedFromManager() {
        ArrayList<Book> bookList = new ArrayList<>();

        bookList.add(new Book());
        bookList.add(new Book());

        when(bookManager.findAll()).thenReturn(bookList);

        inventoryForm.init();

        Assert.assertEquals(inventoryForm.getBookList(), bookList);
    }

    /**
     * Verifies that AuthorManager actually populates the list of authors
     */
    @Test
    public void testInit_authorListIsPopulatedFromManager() {
        ArrayList<Author> authorList = new ArrayList<>();

        authorList.add(new Author());
        authorList.add(new Author());

        when(authorManager.findAll()).thenReturn(authorList);

        inventoryForm.init();
        
        Assert.assertEquals(inventoryForm.getAuthorList(), authorList);
    }

    /**
     * Verifies that the selectedBook is not null
     */
    @Test
    public void testInit_selectedBookIsNotNull() {
        ArrayList<Book> bookList = new ArrayList<>();
        ArrayList<Author> authorList = new ArrayList<>();

        when(bookManager.findAll()).thenReturn(bookList);
        when(authorManager.findAll()).thenReturn(authorList);

        inventoryForm.init();

        Assert.assertNotNull(inventoryForm.getSelectedBook());
    }

    /**
     * Verifies that the selectedAuthor is not null
     */
    @Test
    public void testInit_selectedAuthorIsNotNull() {
        ArrayList<Book> bookList = new ArrayList<>();
        ArrayList<Author> authorList = new ArrayList<>();
        
        when(bookManager.findAll()).thenReturn(bookList);
        when(authorManager.findAll()).thenReturn(authorList);

        inventoryForm.init();

        Assert.assertNotNull(inventoryForm.getSelectedAuthor());
    }

    /**
     * Verifies that activeAuthorList contains only authors where active = true
     */
    @Test
    public void testInit_activeAuthorListContainsOnlyActiveAuthors() {
        List<Author> allAuthors = Arrays.asList(
            createAuthor("Active Author One", true),
            createAuthor("Inactive Author", false),
            createAuthor("Active Author Two", true)
        );

        when(bookManager.findAll()).thenReturn(new ArrayList<>());
        when(authorManager.findAll()).thenReturn(allAuthors);

        inventoryForm.init();

        List<Author> activeAuthors = inventoryForm.getActiveAuthorList();

        Assert.assertEquals(activeAuthors.size(), 2,
            "activeAuthorList should only contain authors where active = true");

        for (Author author : activeAuthors) {
            Assert.assertTrue(author.isActive(),
                "Every author in activeAuthorList should have active = true");
        }
    }

    // completeAuthor() tests

    /**
     * Verifies that completeAuthor() returns authos whose name contains
     * the search query, case insensitive
     */
    @Test
    public void testCompleteAuthor_returnsMatchingAuthors() {
        List<Author> allAuthors = Arrays.asList(
            createAuthor("Stephen King", true),
            createAuthor("Stephen Hawking", true),
            createAuthor("Mark Lawrence", true)
        );

        when(bookManager.findAll()).thenReturn(new ArrayList<>());
        when(authorManager.findAll()).thenReturn(allAuthors);

        inventoryForm.init();

        List<Author> results = inventoryForm.completeAuthor("stephen");

        Assert.assertEquals(results.size(), 2,
            "completeAuthor() should return both authors whose name contains 'stephen'");
    }

    /**
     * Verifies that completeAuthor() is fully case-insensitive - searching with all-caps
     * should still find matches
     */
    @Test
    public void testCompleteAuthor_isCaseInsensitive() {
        List<Author> allAuthors = Arrays.asList(
            createAuthor("Mark Lawrence", true)
        );

        when(bookManager.findAll()).thenReturn(new ArrayList<>());
        when(authorManager.findAll()).thenReturn(allAuthors);

        inventoryForm.init();

        List<Author> results = inventoryForm.completeAuthor("MARK");

        Assert.assertEquals(results.size(), 1,
            "completeAuthor() shoudl match regardless of case");
    }

    /**
     * Verifies that completeAuthor() returns an empty list when the query
     * matches no active authors
     */
    @Test
    public void testCompleteAuthor_returnsEmptyListWhenNoMatch() {
        List<Author> allAuthors = Arrays.asList(
            createAuthor("Mark Lawrence", true)
        );

        when(bookManager.findAll()).thenReturn(new ArrayList<>());
        when(authorManager.findAll()).thenReturn(allAuthors);

        inventoryForm.init();

        List<Author> results = inventoryForm.completeAuthor("Tolkien");

        Assert.assertEquals(results.size(), 0,
            "completeAuthor() should return an empty list when no authors match");
    }

    /**
     * Verifies that completeAuthor() returns results sorted alphabetically 
     * by author name regardless of where they appear in the list
     */
    @Test
    public void testCompleteAuthor_returnsSortedAlphabetically() {
        List<Author> allAuthors = Arrays.asList(
            createAuthor("Stephen King", true),
            createAuthor("Stephen Crane", true),
            createAuthor("Stephen Hawking", true)
        );

        when(bookManager.findAll()).thenReturn(new ArrayList<>());
        when(authorManager.findAll()).thenReturn(allAuthors);

        inventoryForm.init();

        List<Author> results = inventoryForm.completeAuthor("stephen");

        Assert.assertEquals(results.get(0).getName(), "Stephen Crane",
            "First result should be Stephen Crane alphabetically");
        Assert.assertEquals(results.get(1).getName(), "Stephen Hawking",
            "Second Result should be Stephen Hawking alphabetically");
        Assert.assertEquals(results.get(2).getName(), "Stephen King",
            "Third result should be Stephen King alphabetically");
    }

    /**
     * Verifies that inactive authors are excluded from completeAuthor() results even
     * if their name matches the query
     */
    @Test
    public void testCompleteAuthor_excludesInactiveAuthors() {
        List<Author> allAuthors = Arrays.asList(
            createAuthor("Stephen King", true),
            createAuthor("Stephen Hawking", false)
        );

        when(bookManager.findAll()).thenReturn(new ArrayList<>());
        when(authorManager.findAll()).thenReturn(allAuthors);

        inventoryForm.init();

        List<Author> results = inventoryForm.completeAuthor("stephen");

        Assert.assertEquals(results.size(), 1,
            "completeAuthor() should never return inactive authors");
        Assert.assertEquals(results.get(0).getName(), "Stephen King",
            "Only the active Stephen King should be returned");
    }
}
