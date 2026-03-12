package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import com.ksm.bookstore.qualifier.DatasourceSchedule;

@Entity
@Table(name = "ITEMS", schema = DatasourceSchedule.SCHEMA)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
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

    // Constructor + Getters and Setters
    public OrderItem() {}

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Doesn't need setter, hibernate generates it
    public Long getOrderItemId() {
        return orderItemId;
    }
}
