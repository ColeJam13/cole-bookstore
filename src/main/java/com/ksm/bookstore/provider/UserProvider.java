package com.ksm.bookstore.provider;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named
public class UserProvider implements Serializable {

   private static final long serialVersionUID = 1L;

   @Inject
   private FacesContext facesContext;

   /**
    * logs the current user out and redirects to the homepage
    * @throws IOException
    */
   public void logout() throws IOException {
      ExternalContext ec = facesContext.getExternalContext();
      ec.invalidateSession();
      ec.redirect(ec.getRequestContextPath() + "/pages/public/home.xhtml");
   }

   public String getUserName() {
      return facesContext.getExternalContext().getRemoteUser();
   }

   public boolean isAdminUser() {
      return facesContext.getExternalContext().isUserInRole(AccessProvider.ADMIN);
   }

   public boolean isGeneralUser() {
      return facesContext.getExternalContext().isUserInRole(AccessProvider.GENERAL);
   }

   public boolean isUserLoggedIn() {
      return (getUserName() != null);
   }
}
