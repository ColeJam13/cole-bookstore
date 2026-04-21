package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.dao.OrderManager;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Form that handles persisting the data for the admin dashboard
 */
@Named
@ViewScoped
@Getter
@Setter
public class DashboardForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BookManager bookManager;

    @Inject
    private OrderManager orderManager;

    @Inject 
    private AuthorManager authorManager;

    private long totalBooks;
    private long totalOrders;
    private long totalAuthors;

    /**
     * On page view, gets the total for each of the specified fields and
     * populates them on the admin dashboard
     */
    @PostConstruct
    public void init() {
        totalBooks = bookManager.count();
        totalOrders = orderManager.count();
        totalAuthors = authorManager.count();
    }
    
}
