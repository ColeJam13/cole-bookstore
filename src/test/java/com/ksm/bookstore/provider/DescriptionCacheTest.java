package com.ksm.bookstore.provider;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for the DescriptionCache provider
 */
public class DescriptionCacheTest {
    
    private DescriptionCache descriptionCache;

    /**
     * Runs before all @Test methods in this class, Creates
     * a fresh DescriptionCache and manually fires init() so
     * the internal HashMap is initiated before each test runs
     */
    @BeforeMethod
    public void setUp() {
        descriptionCache = new DescriptionCache();
        descriptionCache.init();
    }

    /**
     * Verifies that requesting a description for an ISBN that has
     * never been cached returns null - as it should from an empty HashMap
     */
    @Test
    public void testGetDescription_returnsNullForUnknownIsbn() {
        String result = descriptionCache.getDescription("97800000000000");
        Assert.assertNull(result, "Expected null for an ISBN that has not been cached");
    }

    /**
     * Verifies that after putting a description into the cache, the
     * same value is returned when retrieved by the same ISBN
     */
    @Test
    public void testGetDescription_returnsValueAfterPut() {
        String isbn = "9780132350884";
        String description = "A book about the adventures of a nobel warrior.";

        descriptionCache.putDescription(isbn, description);

        String result = descriptionCache.getDescription(isbn);

        Assert.assertEquals(result, description, "Expected to retrieve the same description that was stored");
    }

    /**
     * Verifies that putting a second description under an existing ISBN overwrites the original
     * Cache should always hold the most recently store value for a given key
     */
    @Test
    public void testPutDescription_overwritesExistingEntry() {
        String isbn = "9780132350884";

        descriptionCache.putDescription(isbn, "Original description.");
        descriptionCache.putDescription(isbn, "Updated description.");

        String result = descriptionCache.getDescription(isbn);

        Assert.assertEquals(result, "Updated description.", "Expected the updated description to overwrite the original");
    }

    /**
     * Verifies that storing a description for one ISBN does not interfere with
     * lookups for a different ISBN. Each key in the HashMap should remain independent
     */
    @Test
    public void testGetDescription_doesNotCrossContaminateEntries() {
        descriptionCache.putDescription("978011111111", "Description for book one.");

        String result = descriptionCache.getDescription("978022222222");

        Assert.assertNull(result, "Expected null for an ISBN that was never cached, even when other ISBNs exist");
    }

}
