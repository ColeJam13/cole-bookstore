package com.ksm.bookstore.form;

import lombok.Getter;
import lombok.Setter;

import com.ksm.bookstore.dao.AuthorManager;
import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.jpa.Book;
import com.ksm.bookstore.jpa.Author;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.Serializable;

import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Form that holds the state data for the admin inventory methods
 */
@Named
@ViewScoped
@Getter
@Setter
public class InventoryForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Book> bookList;

    private List<Author> authorList;

    private Book selectedBook;

    private Author selectedAuthor;

    private List<Author> activeAuthorList;

    @Inject
    private BookManager bookManager;

    @Inject
    private AuthorManager authorManager;

    @PostConstruct
    public void init() {
        bookList = bookManager.findAll();
        authorList = authorManager.findAll();
        selectedBook = new Book();
        selectedAuthor = new Author();
        activeAuthorList = authorList.stream().filter(Author::isActive)
                .collect(Collectors.toList());
    }

    /**
     * Method that takes the input typed by the admin, searches active authors, and
     * returns a filtered, alphabetically sorted, and limited list of matching authors
     * @param query the author searched by the admin
     * @return list of up to 20 matching authors
     */
    public List<Author> completeAuthor(String query) {
        return activeAuthorList.stream()
            .filter(a -> a.getName().toLowerCase().contains(query.toLowerCase()))
            .sorted(Comparator.comparing(Author::getName))
            .limit(10)
            .collect(Collectors.toList());
    }
}
