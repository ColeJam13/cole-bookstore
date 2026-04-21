package com.ksm.bookstore.controller;

import com.ksm.bookstore.form.DashboardForm;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 * Controller for the admin dashboard page of the bookstore application.
 * Handles all admin actions
 */

@Named
@RequestScoped
public class DashboardController {

    @Inject
    private DashboardForm dashboardForm;
    
}