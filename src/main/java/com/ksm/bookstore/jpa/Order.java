package com.ksm.bookstore.jpa;

import com.ksm.bookstore.util.TableConstants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.ksm.bookstore.model.OrderStatus;

/**
 * Entity representing an Order (or cart). Shares many (orders) to one (customer)
 * relationship with Customer, and a One (order) to many (order items) relationship
 * with OrderItems
 * 
 */

@Entity
@Table(name = TableConstants.BOOK_ORDER)
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_NUMBER", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long orderNumber;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @NotNull
    @Column(name = "ORDER_TOTAL", nullable = false)
    private BigDecimal orderTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

}
