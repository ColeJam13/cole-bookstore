package com.ksm.bookstore.provider;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.jpa.Author;

import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.testng.annotations.Test;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Unit tests for {@link AuthorConverter}
 */
public class AuthorConverterTest {

    private static final class Mocking {

        @InjectMocks
        AuthorConverter converter;

        Author authorMock = mock(Author.class);

        AuthorManager authorManagerMock = mock(AuthorManager.class);

        public Mocking() {
            openMocks(this);
            when(authorMock.getAuthorId()).thenReturn(42L);
            when(authorManagerMock.findById(42L)).thenReturn(authorMock);
        }
    }

    // getAsString() tests

    @Test(description = "getAsString() should return an empty string when the author is null")
    public void getAsString_returnsEmptyStringWhenAuthorIsNull() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        String result = m.converter.getAsString(null, null, null);

        // Assert
        assertEquals(result, "");
    }

    @Test(description = "getAsString() should return the author's ID as a string when the author is not null")
    public void getAsString_returnsAuthorIdAsString() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        String result = m.converter.getAsString(null, null, m.authorMock);

        // Assert
        assertEquals(result, "42");
    }

    // getAsObject() tests

    @Test(description = "getAsObject() should return null when the value is null")
    public void getAsObject_returnsNullWhenValueIsNull() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        Author result = m.converter.getAsObject(null, null, null);

        // Assert
        assertNull(result);
    }

    @Test(description = "getAsObject() should return null when the value is an empty string")
    public void getAsObject_returnsNullWhenValueIsEmpty() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        Author result = m.converter.getAsObject(null, null, "");

        // Assert
        assertNull(result);
    }

    @SuppressWarnings("unchecked")
    @Test(description = "getAsObject() should look up and return the author by ID via the CDI container")
    public void getAsObject_loadsAuthorByIdFromCdi() {
        // Arrange
        Mocking m = new Mocking();

        try (MockedStatic<CDI> mockedCdi = mockStatic(CDI.class)) {
            CDI<Object> cdiMock = mock(CDI.class);
            Instance<AuthorManager> instanceMock = mock(Instance.class);
            mockedCdi.when(CDI::current).thenReturn(cdiMock);
            when(cdiMock.select(AuthorManager.class)).thenReturn(instanceMock);
            when(instanceMock.get()).thenReturn(m.authorManagerMock);

            // Act 
            Author result = m.converter.getAsObject(null, null, "42");

            // Assert
            assertEquals(result, m.authorMock);
        }
    }
}