package com.ksm.bookstore.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.service.BookService;
import com.ksm.bookstore.service.OrderService;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Named;

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
     * Adds the selected book to the cart when the user clicks "Add to Cart"
     * @param book
     */
    public void addToCart(Book book) {
        cartItems.add(book);
    }
    
    /**
     * Removes the selected book from the cart when the user clicks "Remove from Cart"
     * @param book
     */
    public void removeFromCart(Book book) {
        cartItems.remove(book);
    }

    /**
     * Loops through the list of selected books in the cart and returns 
     * the sum of total prices in the cart
     * @return
     */
    public double getTotal() {
        double cartTotal = 0.0;
        for (Book book : cartItems) {
            cartTotal = cartTotal + book.getPrice().doubleValue();
        }
        return cartTotal;
    }
}
