package com.ksm.bookstore.provider;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@SessionScoped
@Named
public class UserProvider implements Serializable {
   private static final long serialVersionUID = 1L;

   /**
    * logs the current user out and redirects to the homepage
    * @throws IOException
    */
   public void logout() throws IOException {
      ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
      ec.invalidateSession();
      ec.redirect(ec.getRequestContextPath() + "/pages/public/home.xhtml");
   }

   public String getUserName() {
      return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
   }

   public boolean isAdminUser() {
      return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(AccessProvider.ADMIN);
   }

   public boolean isGeneralUser() {
      return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(AccessProvider.GENERAL);
   }

   public boolean isUserLoggedIn() {
      return (getUserName() != null);
   }

}
