package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link BookManager}
 */
public class BookManagerTest {

    private static final class Mocking {

        @InjectMocks
        BookManager bookManager;

        @Mock
        EntityManager entityManager;

        @Mock
        TypedQuery<Book> bookQuery;

        final Author author = new Author();

        final Book book = createBook("Prince of Thorns", "9780756404079");

        public Mocking() {
            openMocks(this);
            when(entityManager.createQuery(anyString(), eq(Book.class))).thenReturn(bookQuery);
            when(bookQuery.setParameter(anyString(), any())).thenReturn(bookQuery);
            when(bookQuery.getSingleResult()).thenReturn(book);
            when(bookQuery.getResultList()).thenReturn(List.of(book));
        }

        private Book createBook(String title, String isbn) {
            Book b = new Book();
            b.setTitle(title);
            b.setIsbn(isbn);
            return b;
        }
    }

    // findByTitle() tests

    @Test(description = "findByTitle() should return matching book when found")
    public void findByTitle_returnsBookWhenFound() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        Book result = m.bookManager.findByTitle("Prince of Thorns");

        // Assert
        assertNotNull(result);
        assertEquals(result.getTitle(), "Prince of Thorns");
    }

    @Test(description = "findByTitle() should return null when no book matches the given title")
    public void findByTitle_returnsNullWhenNotFound() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookQuery.getSingleResult()).thenThrow(new NoResultException());

        // Act
        Book result = m.bookManager.findByTitle("Unknown Title");

        // Assert
        assertNull(result);
    }

    // findByIsbn() tests

    @Test(description = "findByIsbn() should return the matching book when found")
    public void findByIsbn_returnsBookWhenFound() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        Book result = m.bookManager.findByIsbn("9780756404079");

        // Assert
        assertNotNull(result);
        assertEquals(result.getIsbn(), "9780756404079");
    }

    @Test(description = "findByIsbn() should return null when no book matches the given ISBN")
    public void findByIsbn_returnsNullWhenNotFound() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookQuery.getSingleResult()).thenThrow(new NoResultException());

        // Act
        Book result = m.bookManager.findByIsbn("00000000000");

        // Assert
        assertNull(result);
    }

    // findByAuthor() tests

    @Test(description = "findByAuthor() should return a list of books by the given author")
    public void findByAuthor_returnsBooksForAuthor() {
        // Arrange
        Mocking m = new Mocking();
        
        // Act
        List<Book> result = m.bookManager.findByAuthor(m.author);

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), m.book);
    }

    @Test(description = "findByAuthor() should return an empty list when the author has no books")
    public void findByAuthor_returnsEmptyListWhenNoBooksFound() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookQuery.getResultList()).thenReturn(List.of());

        // Act
        List<Book> result = m.bookManager.findByAuthor(m.author);

        // Assert
        assertTrue(result.isEmpty());
    }

    // findAllActiveBooks() test

    @Test(description = "findAllActiveBooks() should return a list of active books")
    public void findAllActiveBooks_returnsActiveBooks() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        List<Book> result = m.bookManager.findAllActiveBooks();

        // Assert
        assertNotNull(result);
        assertEquals(result.get(0), m.book);
    }

    // findAll() tests

    @Test(description = "findAll() should return all books with authors eagerly loaded")
    public void findAll_returnsAllBooksWithAuthors() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        List<Book> result = m.bookManager.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

    @Test(description = "findAll() should return an empty list when no books exist")
    public void findAll_returnsEmptyListWhenNoBooksExist() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookQuery.getResultList()).thenReturn(List.of());

        // Act
        List<Book> result = m.bookManager.findAll();

        // Assert
        assertTrue(result.isEmpty());
    }
}