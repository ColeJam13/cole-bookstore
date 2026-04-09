package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
 */

@Entity
@Table(name = Book.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Book {

    static final String TABLE_NAME = "BOOK";
    private static final String SEQUENCE_NAME = "BOOK_ID_SEQ";

    @Id
    @SequenceGenerator(name = "BOOK_ID_GENERATOR", sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GENERATOR")
    @Setter(AccessLevel.NONE)
    @Column(name = "BOOK_ID")
    private Long bookId;

    @NotNull(message = "Title {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 255)
    @Column(name = "BOOK_TITLE", length = 255, nullable = false, unique = true)
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private Author author;

    @NotNull(message = "Isbn {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 13)
    @Column(name = "ISBN", length = 13, nullable = false, unique = true)
    private String isbn;

    @NotNull(message = "Price {javax.validation.constraints.NotNull.message}")
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "ACTIVE", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;
}
