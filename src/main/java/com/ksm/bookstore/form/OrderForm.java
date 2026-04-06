package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.dao.OrderManager;
import com.ksm.bookstore.jpa.Order;

import java.io.Serializable;
import java.util.List;

import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Form that handles persisting the data from a customers Order
 */
@Named
@ViewScoped
@Getter
@Setter
public class OrderForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Order> orderList;

    @Inject
    private OrderManager orderManager;

    @PostConstruct
    public void init() {
        orderList = orderManager.findAll();
    }
    
}
