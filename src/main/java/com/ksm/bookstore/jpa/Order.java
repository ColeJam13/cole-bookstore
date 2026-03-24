package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = Order.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Order {

    static final String TABLE_NAME = "BOOK_ORDER";
    private static final String SEQUENCE_NAME = "BOOK_ORDER_ID_SEQ";

    @Id
    @SequenceGenerator(name = "BOOK_ORDER_ID_GENERATOR", sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ORDER_ID_GENERATOR")
    @Setter(AccessLevel.NONE)
    @Column(name = "BOOK_ORDER_ID")
    private Long orderNumber;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @NotNull(message = "Order Total {javax.validation.constraints.NotNull.message}")
    @Column(name = "ORDER_TOTAL", nullable = false)
    private BigDecimal orderTotal;

    @NotNull(message = "Order Status {javax.validation.constraints.NotNull.message}")
    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

}
