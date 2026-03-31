package com.ksm.bookstore.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.service.BookService;
import com.ksm.bookstore.service.OrderService;

import lombok.Getter;
import lombok.Setter;

/**
 * Controller for the shopping cart page of the bookstore application.
 * Handles adding, removing, and displaying books in the users cart.
 * Communicates with BookService and OrderService to manage cart contents.
 */

@Named
@SessionScoped
@Getter
@Setter
public class CartController implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final double TAX_RATE = 0.06;

    @Inject
    private BookService bookService;

    @Inject
    private OrderService orderService;

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
     * @return
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
     * @return
     */
    public double getTax() {
        return getSubTotal() * TAX_RATE;
    }

    /**
     * Method that returns the carts Total
     * @return
     */
    public double getTotal() {
        return getSubTotal() + getTax();
    }

    /**
     * Clears all items from the cart and redirects to the home page
     * @throws IOException
     */
    public void clearCart() throws IOException {
        cartItems.clear();
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .redirect("home.jsf");
    }

}
