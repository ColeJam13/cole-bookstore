package com.ksm.bookstore.controller;

import java.io.Serializable;
import java.io.IOException;

import javax.faces.context.FacesContext;

import com.ksm.bookstore.form.BookSearchForm;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the public home page of the bookstore application.
 * Handles book browsing and search functionality for public users.
 */

@Named
@RequestScoped
@Getter
@Setter
public class HomeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BookSearchForm bookSearchForm;

    /**
     * Method that navigates to the book detail pages via ISBN when 
     * a user clicks that books row
     * @throws IOException
     */
    public void navigate() throws IOException {
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .redirect("book-detail.jsf?isbn=" + bookSearchForm.getSelectedBook().getIsbn());
    }

}
