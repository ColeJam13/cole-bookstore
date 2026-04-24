package com.ksm.bookstore.provider;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Unit tests for {@link DescriptionCache}
 */
public class DescriptionCacheTest {

    private static final class Mocking {

        DescriptionCache cache;

        public Mocking() {
            cache = new DescriptionCache();
            cache.init();
        }
    }

    // getDescription() tests

    @Test(description = "getDescription() should return null for an ISBN that has never been cached")
    public void getDescription_returnsNullForUnknownIsbn() {
        // Arrange
        Mocking m = new Mocking();

        // Act
        String result = m.cache.getDescription("97800000000000");

        // Assert
        assertNull(result);
    }

    @Test(description = "getDescription() should return the correct value after putDescription() stores it")
    public void getDescription_returnsValueAfterPut() {
        // Arrange
        Mocking m = new Mocking();
        String isbn = "9780132350884";
        String description = "A book about the adventures of a noble warrior.";

        // Act
        m.cache.putDescription(isbn, description);
        String result = m.cache.getDescription(isbn);

        // Assert
        assertEquals(result, description);
    }

    @Test(description = "putDescription() should overwrite an existing entry for the same ISBN")
    public void putDescription_overwritesExistingEntry() {
        //Arrange
        Mocking m = new Mocking();
        String isbn = "9780132350884";

        // Act
        m.cache.putDescription(isbn, "Original description.");
        m.cache.putDescription(isbn, "Updated description.");
        String result = m.cache.getDescription(isbn);

        // Assert
        assertEquals(result, "Updated description.");
    }

    @Test(description = "getDescription() should return null for an ISBN that was never cached")
    public void getDescription_doesNotCrossContaminateEntries() {
        // Arrange
        Mocking m = new Mocking();
        m.cache.putDescription("9780111111111", "Description for book one.");

        // Act
        String result = m.cache.getDescription("9780222222222");

        // Assert
        assertNull(result);
    }
}