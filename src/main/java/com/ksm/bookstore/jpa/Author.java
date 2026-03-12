package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ksm.bookstore.qualifier.DatasourceSchedule;

@Entity
@Table(name="AUTHOR", schema = DatasourceSchedule.SCHEMA)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name="AUTHOR_NAME", length=50, nullable=false, unique=false)
    private String name;

    public Author() {}      // Constructor

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {       // Doesn't need setter, hibernate generates it
        return id;
    }
}
