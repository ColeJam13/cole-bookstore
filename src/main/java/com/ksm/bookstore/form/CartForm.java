package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.jpa.Book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Form that handles persisting all the data from the customers cart
 */
@Named
@SessionScoped
@Getter
@Setter
public class CartForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Book> cartItems;

    private static final double TAX_RATE = 0.06;
    
    
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
     * Creates a new ArrayList of books when the page constructs
     */
    @PostConstruct
    public void init() {
        cartItems = new ArrayList<>();
    }
}
