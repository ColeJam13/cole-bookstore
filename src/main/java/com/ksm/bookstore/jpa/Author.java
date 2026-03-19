package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Entity representing an author of a book object.
 * 
 */

@Entity
@Table(name = Author.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Author {

    static final String TABLE_NAME = "AUTHOR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long authorId;

    @NotNull(message = "Author Name {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 50)
    @Column(name = "AUTHOR_NAME", length = 50, nullable = false)
    private String name;

}
