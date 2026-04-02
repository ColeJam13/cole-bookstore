package com.ksm.bookstore.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.ksm.bookstore.jpa.Book;

import lombok.Getter;
import lombok.Setter;

/**
 * Controller for the shopping cart page of the bookstore application.
 * Handles adding, removing, and displaying books in the users cart.
 */

@Named
@SessionScoped
@Getter
@Setter
public class CartController implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final double TAX_RATE = 0.06;

    private List<Book> cartItems;

    /**
     * Creates a new ArrayList of books when the page constructs
     */
    @PostConstruct
    public void init() {
        cartItems = new ArrayList<>();
    }

    /**
     * Method that adds the selected book to the cart when the user clicks "Add to Cart"
     * @param book
     */
    public void addToCart(Book book) {
        cartItems.add(book);
    }
    
    /**
     * Method that removes the selected book from the cart when the user clicks "Remove from Cart"
     * @param book
     */
    public void removeFromCart(Book book) throws IOException {
        cartItems.remove(book);
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .redirect("cart.jsf");
    }

    /**
     * Method that loops through the list of selected books in the cart and returns 
     * the subtotal of the book prices in the cart
     * @return the subtotal of all items in cart
     */
    public double getSubTotal() {
        double cartTotal = 0.0;
        for (Book book : cartItems) {
            cartTotal = cartTotal + book.getPrice().doubleValue();
        }
        return cartTotal;
    }

    /**
     * Method that calculates the carts total tax amount based on the items in the cart
     * @return the tax based on items in cart
     */
    public double getTax() {
        return getSubTotal() * TAX_RATE;
    }

    /**
     * Method that returns the carts Total
     * @return Total of the Cart
     */
    public double getTotal() {
        return getSubTotal() + getTax();
    }

    /**
     * Clears all items from the cart
     */
    public void clearCart() {
        cartItems.clear();
    }

}
