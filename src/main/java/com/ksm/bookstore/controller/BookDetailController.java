package com.ksm.bookstore.controller;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ksm.bookstore.service.BookService;
import com.ksm.bookstore.jpa.Book;

import lombok.Getter;
import lombok.Setter;

/**
 * Controller for the Book Details page. Handles fetching
 * book details via ISBN
 */

@Named
@ViewScoped
@Getter
@Setter
public class BookDetailController implements Serializable{

    private static final long serialVersionUID = 1L;

    @Inject
    private BookService bookService;

    private Book book;

    private String description;

    private String isbn;

    /**
     * Loads the book details for the requested ISBN.
     * Triggered by f:viewAction after view parameters have been injected,
     * ensuring the ISBN is available before the book is fetched.
     */
    public void init() {
        if (isbn == null) {
            return;
        }
        book = bookService.getBookByIsbn(isbn);
    }
}
