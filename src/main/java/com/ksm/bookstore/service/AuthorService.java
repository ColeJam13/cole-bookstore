package com.ksm.bookstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.AuthorManager;

/**
 * Service class containing business logic for Author related operations.
 * Acts as an intermediary between AuthorManager and the controller layer.
 */

@ApplicationScoped
public class AuthorService {

    @Inject
    private AuthorManager authorManager;
    
}
