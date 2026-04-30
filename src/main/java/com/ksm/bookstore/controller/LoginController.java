package com.ksm.bookstore.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;

import com.ksm.bookstore.form.LoginForm;

/**
 * Controller designed to allow login by both a general user and an admin
 * within the same dialogue. Will throw an error with an invalid or null
 * username or password.
 */
@Named
@RequestScoped
public class LoginController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private LoginForm loginForm;

    /**
     * Helper method which looks up a localized message from appmsg resource bundle
     * @param key the message key to look up in the bundle
     * @return the localized message string
     */
    private String getMessage(String key) {
        return ResourceBundle.getBundle("com.ksm.web.ui.application",
            facesContext.getViewRoot().getLocale()).getString(key);
    }

    /**
     * Resets the login form fields when the dialog is closed
     */
    public void clearForm() {
        loginForm.setUsername(null);
        loginForm.setPassword(null);
        loginForm.setLoginFailed(false);
    }

    /**
     * Attempts a login via the inputted username and password, throws error message in incorrect or null
     * @throws IOException if the post-login redirect fails
     */
    public void login() throws IOException {
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            request.login(loginForm.getUsername(), loginForm.getPassword());
            ExternalContext ec = facesContext.getExternalContext();
            String viewId = facesContext.getViewRoot().getViewId();
            ec.redirect(ec.getRequestContextPath() + viewId.replace(".xhtml", ".jsf"));
        } catch (ServletException e) {
            loginForm.setLoginFailed(true);
            loginForm.setPassword(null);
            PrimeFaces.current().executeScript("PF('loginDialog').show()");
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("message.login.invalid.credentials"), null));
        }
    }
}
