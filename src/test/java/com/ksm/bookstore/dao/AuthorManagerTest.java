package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Author;

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
 * Unit tests for {@link AuthorManager}
*/
public class AuthorManagerTest {

    private static final class Mocking {

        @InjectMocks
        AuthorManager authorManager;

        @Mock
        EntityManager entityManager;

        @Mock
        TypedQuery<Author> authorQuery;

        Author author = createAuthor("Mark Lawrence");

        public Mocking() {
            openMocks(this);
            when(entityManager.createQuery(anyString(), eq(Author.class))).thenReturn(authorQuery);
            when(authorQuery.setParameter(anyString(), any())).thenReturn(authorQuery);
            when(authorQuery.getSingleResult()).thenReturn(author);
            when(authorQuery.getResultList()).thenReturn(List.of(author));
        }

        private Author createAuthor(String name) {
            Author a = new Author();
            a.setName(name);
            return a;
        }
    }

    // findyName() tests

    @Test(description = "findByName() should return the matching author when found")
    public void findByName_returnsAuthorWhenFound() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        Author result = m.authorManager.findByName("Mark Lawrence");

        // Assert
        assertNotNull(result);
        assertEquals(result.getName(), "Mark Lawrence");
    }

    @Test(description = "findByName() should return null when no author matches the given name")
    public void findByName_returnsNullWhenNotFound() {
        // Arrange
        Mocking m = new Mocking();
        when(m.authorQuery.getSingleResult()).thenThrow(new NoResultException());

        // Act
        Author result = m.authorManager.findByName("Unknown Author");

        // Assert
        assertNull(result);
    }

    // findAllActiveAuthors() tests

    @Test(description = "findAllActiveAuthors() should return a list of active authors")
    public void findAllActiveAuthors_returnsActiveAuthors() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        List<Author> result = m.authorManager.findAllActiveAuthors();

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), m.author);
    }

    @Test(description = "findAllActiveAuthors() should return an empty list when no active authors exist")
    public void findAllActiveAuthors_returnsEmptyListWhenNoneActive() {
        // Arrange
        Mocking m = new Mocking();
        when(m.authorQuery.getResultList()).thenReturn(List.of());

        // Act
        List<Author> result = m.authorManager.findAllActiveAuthors();

        // Assert
        assertTrue(result.isEmpty());
    }
}