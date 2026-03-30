package com.ksm.bookstore.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;

import org.omnifaces.cdi.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArray;

import com.ksm.bookstore.service.BookService;
import com.ksm.bookstore.jpa.Book;

import lombok.Getter;
import lombok.Setter;

/**
 * Controller for the Book Details page. Handles fetching
 * book details via ISBN
 */

@Named
@ViewScoped
@Getter
@Setter
public class BookDetailController implements Serializable{

    private static final long serialVersionUID = 1L;

    @Inject
    private BookService bookService;

    private Book book;

    private String description;

    private String isbn;

    /**
     * Fetches a description for each book by using the Subtitle field on GoogleBooks API
     * via the books ISBN. Key is located within the Standalone file
     * @throws IOException
     */

    private void fetchDescription() throws IOException {
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&key=" + System.getProperty("google.books.api.key");
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JsonObject jsonResponse = Json.createReader(
            new java.io.StringReader(response.toString())
        ).readObject();

        JsonArray items = jsonResponse.getJsonArray("items");

        if (items != null && !items.isEmpty()) {
            JsonObject volumeInfo = items.getJsonObject(0).getJsonObject("volumeInfo");
            if (volumeInfo.containsKey("description")) {
                description = volumeInfo.getString("description");
            }
        }
    }


    /**
     * Loads the book details for the requested ISBN.
     * Triggered by f:viewAction after view parameters have been injected,
     * ensuring the ISBN is available before the book is fetched.
     */
    public void init() {
        if (isbn == null) {
            return;
        }
        book = bookService.getBookByIsbn(isbn);
        
        try {
            fetchDescription();
        } catch (IOException e) {
            description = null;
        }
    }
}
