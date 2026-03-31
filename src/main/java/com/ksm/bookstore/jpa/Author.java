package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
    private static final String SEQUENCE_NAME = "AUTHOR_ID_SEQ";

    @Id
    @SequenceGenerator(name = "AUTHOR_ID_GENERATOR", sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHOR_ID_GENERATOR")
    @Setter(AccessLevel.NONE)
    @Column(name = "AUTHOR_ID")
    private Long authorId;

    @NotNull(message = "Author Name {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 50)
    @Column(name = "AUTHOR_NAME", length = 50, nullable = false)
    private String name;

}
