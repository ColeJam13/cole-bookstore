package com.ksm.bookstore.controller;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.form.InventoryForm;
import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.service.AuthorService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
        inventoryForm.init();
    }

    /**
     * Method that will set a books "isActive" flag to false
     * @param book the book to be deactivated
     */
    public void deactivateBook(Book book) {
        book.setActive(false);
        bookManager.update(book);
        inventoryForm.init();
    }

    /**
     * Method that will set a books "isActive" flag to true.
     * Checks to ensure the author is active or throws error message
     * @param book the book to be activated
     */
    public void activateBook(Book book) {
        if (!book.getAuthor().isActive()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Cannot activate book with an inactive author.", null));
        } else {
        book.setActive(true);
        bookManager.update(book);
        inventoryForm.init();
        }
    }

    /**
     * Method that admins can use to create a new Author
     */
    public void newAuthor() {
        inventoryForm.setSelectedAuthor(new Author());
    }

    /**
     * Method that sets the author selected by the admin
     * @param author the Author selected
     */
    public void selectAuthor(Author author) {
        inventoryForm.setSelectedAuthor(author);
    }

    /**
     * Saves an author to the database, creating it if new or updating it if existing
     */
    public void saveAuthor() {
        authorManager.update(inventoryForm.getSelectedAuthor());
        inventoryForm.init();
    }

    /**
     * Method that will deactivate an existing author
     * @param author the Author to be deactivated
     */
    public void deactivateAuthor(Author author) {
        authorService.deactivateAuthor(author);
        inventoryForm.init();
    }

    /**
     * Method that will activate the selected author
     * @param author the Author to be activated
     */
    public void activateAuthor(Author author) {
        author.setActive(true);
        authorManager.update(author);
        inventoryForm.init();
    }
    
}