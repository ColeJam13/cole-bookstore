package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "ITEMS")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    @Setter(AccessLevel.NONE)
    private Long orderItemId;

    // Many to One relationship (Many OrderItems to one Order)
    @ManyToOne
    @JoinColumn(name = "ORDER_NUMBER", nullable = false)
    private Order order;

    // Many to One relationship (Many OrderItems to one Book)
    @ManyToOne
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;
    
    @NotNull
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;
    
}
