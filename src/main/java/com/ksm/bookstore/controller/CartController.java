package com.ksm.bookstore.controller;

import java.io.Serializable;
import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;

import com.ksm.bookstore.form.CartForm;
import com.ksm.bookstore.jpa.Book;

import lombok.Getter;
import lombok.Setter;

/**
 * Controller for the shopping cart page of the bookstore application.
 * Handles adding, removing, and displaying books in the users cart.
 */

@Named
@RequestScoped
@Getter
@Setter
public class CartController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CartForm cartForm;


    /**
     * Method that adds the selected book to the cart when the user clicks "Add to Cart"
     * @param book
     */
    public void addToCart(Book book) {
        cartForm.getCartItems().add(book);
    }
    
    /**
     * Method that removes the selected book from the cart when the user clicks "Remove from Cart"
     * @param book
     */
    public void removeFromCart(Book book) throws IOException {
        cartForm.getCartItems().remove(book);
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .redirect("cart.jsf");
    }

    /**
     * Clears all items from the cart and shows an empty cart
     * @throws IOException
     */
    public void clearCart() throws IOException {
        cartForm.getCartItems().clear();
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .redirect("cart.jsf");
    }

}
