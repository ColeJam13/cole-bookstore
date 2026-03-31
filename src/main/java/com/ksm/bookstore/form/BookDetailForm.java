package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.jpa.Book;

import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
@Getter
@Setter
public class BookDetailForm implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Book book;

    private String description;
}
