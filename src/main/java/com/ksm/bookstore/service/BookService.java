package com.ksm.bookstore.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;


@ApplicationScoped
public class BookService {

    @Inject
    private BookManager bookManager;

    @Inject 
    private AuthorManager authorManager;
    
}
