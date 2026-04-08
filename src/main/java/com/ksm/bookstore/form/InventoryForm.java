package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.jpa.Author;

import java.util.List;
import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Form that holds the state data for the admin inventory methods
 */
@Named
@ViewScoped
@Getter
@Setter
public class InventoryForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Book> bookList;

    private List<Author> authorList;

    private Book selectedBook;

    private Author selectedAuthor;

    @Inject
    private BookManager bookManager;

    @Inject
    private AuthorManager authorManager;

    @PostConstruct
    public void init() {
        bookList = bookManager.findAll();
        authorList = authorManager.findAll();
        selectedBook = new Book();
        selectedAuthor = new Author();
    }
    
}
