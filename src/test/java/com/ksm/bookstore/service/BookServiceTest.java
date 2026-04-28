package com.ksm.bookstore.service;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;

import org.mockito.InjectMocks;
import org.mockito.Mock;
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
 * Unit tests for {@link BookService}
 */
public class BookServiceTest {

    private static final class Mocking {

        @InjectMocks
        BookService bookService;

        @Mock
        AuthorManager authorManager;

        @Mock
        BookManager bookManager;

        final String authorName = "Mark Lawrence";

        final Author author = createAuthor(authorName);

        final List<Book> books = List.of(new Book(), new Book());

        public Mocking() {
            openMocks(this);
            when(authorManager.findByName(authorName)).thenReturn(author);
            when(bookManager.findByAuthor(author)).thenReturn(books);
        }

        // Helper method to create an Author with a name
        public Author createAuthor(String name) {
            Author author = new Author();
            author.setName(name);
            return author;
        }
    }

    // getBookByAuthor() tests - Author found

    @Test(description = "getBookByAuthor() should return the books associated with the found author")
    public void getBookByAuthor_returnsBooksWhenAuthorFound() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        List<Book> result = m.bookService.getBookByAuthor(m.authorName);

        // Assert
        assertEquals(result, m.books);

        // Verify
        verify(m.authorManager).findByName(m.authorName);
        verify(m.bookManager).findByAuthor(m.author);
    }

    @Test(description = "getBookByAuthor() should return an empty list when the author exists but has no books")
    public void getBookByAuthor_returnsEmptyListWhenAuthorHasNoBooks() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookManager.findByAuthor(m.author)).thenReturn(new ArrayList<>());

        // Act
        List<Book> result = m.bookService.getBookByAuthor(m.authorName);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // getBookByAuthor test - author not found

    @Test(description = "getBookByAuthor() should return an empty list when the author is not found")
    public void getBookByAuthor_returnsEmptyListWhenAuthorNotFound() {
        // Arrange
        Mocking m = new Mocking();
        when(m.authorManager.findByName("Unknown Author")).thenReturn(null);

        // Act
        List<Book> result = m.bookService.getBookByAuthor("Unknown Author");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}