package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.jpa.Book;

import java.util.List;
import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
@Getter
@Setter
public class BookSearchForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<Book> searchResults;

    private Book selectedBook; 

}
