package com.ksm.bookstore.dao;

import com.ksm.bookstore.jpa.Author;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link BaseManager}
 */
public class BaseManagerTest {

    private static final class Mocking {

        // Creates an anonymous concrete subclass to test since BaseManager is abstract
        @InjectMocks
        BaseManager<Author> baseManager = new BaseManager<Author>(Author.class) {};

        @Mock
        EntityManager entityManager;

        @Mock
        TypedQuery<Author> authorQuery;

        @Mock
        TypedQuery<Long> countQuery;

        Author author = new Author();

        public Mocking() {
            openMocks(this);
            when(entityManager.createQuery(anyString(), eq(Author.class))).thenReturn(authorQuery);
            when(authorQuery.getResultList()).thenReturn(List.of(author));
            when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(countQuery);
            when(countQuery.getSingleResult()).thenReturn(1L);
        }
    }

    // findById() test

    @Test(description = "findById() should delegate to entityManager.find() and return the matching entity")
    public void findById_returnsEntityById() {
        //Arrange
        Mocking m = new Mocking();
        when(m.entityManager.find(Author.class, 1L)).thenReturn(m.author);

        // Act
        Author result = m.baseManager.findById(1L);

        // Assert
        assertEquals(result, m.author);

        // Verify
        verify(m.entityManager).find(Author.class, 1L);
    }

    // findAll() tests

    @Test(description = "findAll() should return all entities of the managed type")
    public void findAll_returnsAllEntities() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        List<Author> result = m.baseManager.findAll();

        // Assert
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), m.author);
    }

    @Test(description = "findAll() should return an empty list when no entities exist")
    public void findAll_returnsEmptyListWhenNoEntitiesExist() {
        // Arrange
        Mocking m = new Mocking();
        when(m.authorQuery.getResultList()).thenReturn(List.of());

        // Act
        List<Author> result = m.baseManager.findAll();

        // Assert
        assertTrue(result.isEmpty());
    }

    // update() test

    @Test(description = "update() should call entityManager.merge() and return the managed entity")
    public void update_callsMergeAndReturnsEntity() {
        // Arrange
        Mocking m = new Mocking();
        when(m.entityManager.merge(m.author)).thenReturn(m.author);

        // Act
        Author result = m.baseManager.update(m.author);

        // Assert
        assertEquals(result, m.author);

        // Verify
        verify(m.entityManager).merge(m.author);
    }

    // delete() test

    @Test(description = "delete() should call entityManager.remove() on the given entity")
    public void delete_callsRemove() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        m.baseManager.delete(m.author);

        // Verify
        verify(m.entityManager).remove(m.author);
    }

    // count() test

    @Test(description = "count() should return the total number of records for the managed entity")
    public void count_returnsTotalCount() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        long result = m.baseManager.count();

        // Assert
        assertEquals(result, 1L);
    }
}
