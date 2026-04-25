package com.ksm.bookstore.service;

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
import static org.testng.Assert.assertFalse;


/**
 * Unit tests for {@link AuthorService}
 */
public class AuthorServiceTest {

    private static final class Mocking {

        @InjectMocks
        AuthorService authorService;

        @Mock
        AuthorManager authorManager;

        @Mock
        BookManager bookManager;

        Author author = createAuthor("Mark Lawrence", true);
        Book bookOne = createBook("Book One", true);
        Book bookTwo = createBook("Book Two", true);
        List<Book> authorBooks = List.of(bookOne, bookTwo);

        public Mocking() {
            openMocks(this);
            when(bookManager.findByAuthor(author)).thenReturn(authorBooks);
            when(authorManager.update(author)).thenReturn(author);
            when(bookManager.update(bookOne)).thenReturn(bookOne);
            when(bookManager.update(bookTwo)).thenReturn(bookTwo);
        }

        // Helper method to create an Author with a name and active status
        public Author createAuthor(String name, boolean active) {
            Author author = new Author();
            author.setName(name);
            author.setActive(active);
            return author;
        }

        // Helper method to create a Book with a title and active status
        public Book createBook(String title, boolean active) {
            Book book = new Book();
            book.setTitle(title);
            book.setActive(active);
            return book;
        }
    }

    // deactivateAuthor() tests

    @Test(description = "deactivateAuthor() should set the author's active flag to false")
    public void deactivateAuthor_setsAuthorInactive() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.authorService.deactivateAuthor(m.author);

        // Assert
        assertFalse(m.author.isActive());
    }

    @Test(description = "deactivateAuthor() should call authorManager.update() with the deactivated author")
    public void deactivateAuthor_persistsAuthorUpdate() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.authorService.deactivateAuthor(m.author);

        // Verify
        verify(m.authorManager).update(m.author);
    }

    @Test(description = "deactivateAuthor() should set all books by that author to inactive")
    public void deactivateAuthor_setsAllAuthorBooksInactive() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.authorService.deactivateAuthor(m.author);

        // Assert
        assertFalse(m.bookOne.isActive());
        assertFalse(m.bookTwo.isActive());
    }

    @Test(description = "deactivateAuthor() should call bookManager.update() for each book by the author")
    public void deactivateAuthor_persistsAllBookUpdates() {
        // Arrange 
        Mocking m = new Mocking();

        // Act
        m.authorService.deactivateAuthor(m.author);

        // Verify
        verify(m.bookManager).update(m.bookOne);
        verify(m.bookManager).update(m.bookTwo);
    }

    @Test(description = "deactivateAuthor() should still deactivate the author when they have no books")
    public void deactivateAuthor_handlesAuthorWithNoBooks() {
        // Arrange
        Mocking m = new Mocking();
        when(m.bookManager.findByAuthor(m.author)).thenReturn(List.of());

        // Act
        m.authorService.deactivateAuthor(m.author);

        // Assert
        assertFalse(m.author.isActive());

        // Verify
        verify(m.authorManager).update(m.author);
    }
}