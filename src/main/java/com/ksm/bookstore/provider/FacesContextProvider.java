package com.ksm.bookstore.provider;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;

/**
 * CDI producer that makes {@link FacesContext} injectable
 */
@RequestScoped
@NoArgsConstructor(access = AccessLevel.NONE)
public class FacesContextProvider {

    /**
     * Produces the current FacesContext instance for CDI injection
     */
    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
}
