package com.ksm.bookstore.util;

/**
 * Utility Class to set the Table names as constants and refer
 * to them in entities
 * 
 */

public final class TableConstants {

    private TableConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }
    
    public static final String ADDRESS = "ADDRESS";

    public static final String AUTHOR = "AUTHOR";

    public static final String BOOK = "BOOK";

    public static final String CUSTOMER = "CUSTOMER";

    public static final String BOOK_ORDER = "BOOK_ORDER";

    public static final String ORDER_ITEM = "ORDER_ITEM";
}
