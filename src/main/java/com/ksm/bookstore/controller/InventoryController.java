package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.form.InventoryForm;
import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.service.AuthorService;

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

    @Inject
    private AuthorService authorService;

    /**
     * Creates a new book when an Admin calls it from InventoryForm
     */
    public void newBook() {
        inventoryForm.setSelectedBook(new Book());
    }

    /**
     * Sets the selected book when called from InventoryForm
     * @param book the book selected
     */
    public void selectBook(Book book) {
        inventoryForm.setSelectedBook(book);
    }

    /**
     * Saves a book to the database, creating it if new or updating if existing
     */
    public void saveBook() {
        bookManager.update(inventoryForm.getSelectedBook());
        inventoryForm.setBookList(bookManager.findAll());
    }

    /**
     * Method that will set a books "isActive" flag to false
     * @param book the book to be deactivated
     */
    public void deactivateBook(Book book) {
        book.setActive(false);
        bookManager.update(book);
        inventoryForm.setBookList(bookManager.findAll());
    }

    /**
     * Method that will set a books "isActive" flag to true
     * @param book the book to be activated
     */
    public void activateBook(Book book) {
        book.setActive(true);
        bookManager.update(book);
        inventoryForm.setBookList(bookManager.findAll());
    }

    /**
     * Saves an author to the database, creating it if new or updating if existing
     */
    public void saveAuthor() {
        authorManager.update(inventoryForm.getSelectedAuthor());
        inventoryForm.setAuthorList(authorManager.findAll());
    }

    /**
     * Method that will deactivate an existing author
     * @param author the Author to be deactivated
     */
    public void deactivateAuthor(Author author) {
        authorService.deactivateAuthor(author);
        inventoryForm.setAuthorList(authorManager.findAll());
    }
    
}