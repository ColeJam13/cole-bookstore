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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Entity representing a book object. Linked to Author by
 * a many (books) to one (author) relationship.
 * 
 * @author Cole
 */

@Entity
@Table(name = "BOOK")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
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

}
