package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;

import com.ksm.bookstore.qualifier.DatasourceSchedule;

@Entity
@Table(name = "BOOK", schema = DatasourceSchedule.SCHEMA)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BOOK_TITLE", length = 50, nullable = false, unique = true)
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private Author author;

    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "ISBN", length = 13, nullable = false, unique = true)
    private String isbn;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    // Constructor + Getters and Setters
    public Book() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor (Author author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Doesn't need setter, hibernate generates it
    public Long getBookId() {
        return bookId;
    }
}
