package com.ksm.bookstore.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;

import com.ksm.bookstore.dao.CustomerManager;
import com.ksm.bookstore.form.ProfileForm;

/**
 * Controller for the regular user profile page. Handles updating the users
 * saved information and showing their order history
 */
@Named
@RequestScoped
public class ProfileController {

    @Inject
    private CustomerManager customerManager;

    @Inject
    private ProfileForm profileForm;

    /**
     * Updates the customers profile information when edited
     */
    public void saveProfile() {
        customerManager.update(profileForm.getCustomer());
    }
    
}
