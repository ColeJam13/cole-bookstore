package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

/**
 * Form that holds the view state data for the checkout page
 */
@Named
@ViewScoped
@Getter
@Setter
public class CheckoutForm implements Serializable{

    private static final long serialVersionUID = 1L;

    private String email;

    private String firstName;

    private String lastName;

    private String streetAddress;

    private String city;

    private String state;

    private String zip;

    private boolean sameAsShipping;

    private String billingStreetAddress;

    private String billingCity;

    private String billingState;

    private String billingZip;
    
}
