package com.ksm.bookstore.model;

/**
 * Enum that contains constants of the OrderStatus. Orders will only
 * ever be in one of these three states.
 * 
 * @author Cole
 */

public enum OrderStatus {
    SUBMITTED,
    CANCELLED,
    COMPLETE
}
