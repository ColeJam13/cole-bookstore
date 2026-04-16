package com.ksm.bookstore.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

/**
 * Controller designed to allow login by both a general user and an admin
 * within the same dialogue. Will throw an error with an invalid or null
 * username or password.
 */
@Named
@RequestScoped
@Getter
@Setter
public class LoginController {

    private String username;

    private String password;

    private boolean loginFailed;

    /**
     * Attempts a login via the inputted username and password, throws error message in incorrect or null
     */
    public void login() throws IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            request.login(username, password);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            ec.redirect(ec.getRequestContextPath() + viewId.replace(".xhtml", ".jsf"));
        } catch (ServletException e) {
            loginFailed = true;
            password = null;
            PrimeFaces.current().executeScript("PF('loginDialog').show()");
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password, please try again.", null));
        }
    }
    
}
