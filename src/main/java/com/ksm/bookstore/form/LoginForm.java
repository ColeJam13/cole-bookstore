package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import javax.inject.Named;

/**
 * Form that holds the view state data for the login actions
 */
@Named
@ViewScoped
@Getter
@Setter
public class LoginForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private boolean loginFailed;
    
}
