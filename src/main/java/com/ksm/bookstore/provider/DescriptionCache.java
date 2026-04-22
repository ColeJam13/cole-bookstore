package com.ksm.bookstore.provider;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Provider that caches book descriptions from Google API to respect rate limits
 */
@Named
@ApplicationScoped
public class DescriptionCache {
    
    private Map<String, String> descriptionCache;

    @PostConstruct
    public void init() {
        descriptionCache = new HashMap<>();
    }

    /**
     * Retrieves a cached description for the given ISBN
     * @param isbn the ISBN to look up
     * @return the cached description, or null if not yet cached
     */
    public String getDescription(String isbn) {
        return descriptionCache.get(isbn);
    }

    /**
     * Stores a description in the cache for a given ISBN
     * @param isbn the ISBN to cache the description for
     * @param description the description to cache
     */
    public void putDescription(String isbn, String description) {
        descriptionCache.put(isbn, description);
    }
}
