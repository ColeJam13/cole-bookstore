package com.ksm.bookstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.AuthorManager;

@ApplicationScoped
public class AuthorService {

    @Inject
    private AuthorManager authorManager;
    
}
