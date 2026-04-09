package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.jpa.Order;
import com.ksm.bookstore.jpa.OrderItem;

import java.io.Serializable;
import java.util.List;

import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
/**
 * Form that holds the view state data for the Confirmation page
 */
@Named
@ViewScoped
@Getter
@Setter
public class ConfirmationForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Order order;

    private List<OrderItem> orderItems;

    @Param
    private Long orderNumber;
}
