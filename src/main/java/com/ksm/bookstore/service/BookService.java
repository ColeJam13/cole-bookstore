package com.ksm.bookstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;

/**
 * Service class containing business logic for Book related operations.
 * Acts as an intermediary between BookManager and AuthorManager and the controller layer.
 */

@ApplicationScoped
public class BookService {

    @Inject
    private BookManager bookManager;

    @Inject 
    private AuthorManager authorManager;
    
}
