package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.form.InventoryForm;
import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the admin inventory management page of the bookstore application.
 * Handles adding, editing, and deleting books and authors for administrators.
 */

@Named
@RequestScoped
public class InventoryController {

    @Inject
    private BookManager bookManager;

    @Inject
    private AuthorManager authorManager;

    @Inject
    private InventoryForm inventoryForm;

    /**
     * Method that will either save a new Book object if the ID does not exist,
     * or updates existing book if ID does exist
     */
    public void saveBook() {
        if (inventoryForm.getSelectedBook().getBookId() == null) {
            bookManager.create(inventoryForm.getSelectedBook());
        } else {
            bookManager.update(inventoryForm.getSelectedBook());
        }
        inventoryForm.setBookList(bookManager.findAll());
    }

    /**
     * Method that will delete an existing book
     * @param book
     */
    public void deleteBook(Book book) {
        bookManager.delete(book);
        inventoryForm.setBookList(bookManager.findAll());
    }

    /**
     * Method that will either save a new Author object if the ID does not exist,
     * or updates existing Author if ID does exist
     */
    public void saveAuthor() {
        if (inventoryForm.getSelectedAuthor().getAuthorId() == null) {
            authorManager.create(inventoryForm.getSelectedAuthor());
        } else {
            authorManager.update(inventoryForm.getSelectedAuthor());
        }
        inventoryForm.setAuthorList(authorManager.findAll());
    }

    /**
     * Method that will delete an existing author
     * @param author
     */
    public void deleteAuthor(Author author) {
        authorManager.delete(author);
        inventoryForm.setAuthorList(authorManager.findAll());
    }
    
}