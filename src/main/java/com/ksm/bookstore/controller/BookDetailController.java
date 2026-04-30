package com.ksm.bookstore.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArray;

import com.ksm.bookstore.dao.BookManager;
import com.ksm.bookstore.form.BookDetailForm;
import com.ksm.bookstore.provider.DescriptionCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the Book Details page. Handles fetching
 * book details via ISBN
 */

@Named
@RequestScoped
public class BookDetailController {

    private static final Logger LOG = LoggerFactory.getLogger(BookDetailController.class);

    @Inject
    private BookManager bookManager;

    @Inject
    private BookDetailForm bookDetailForm;

    @Inject
    private DescriptionCache descriptionCache;

    /**
     * Fetches a description for each book by using the description field on GoogleBooks API
     * via the books ISBN. Key is located within the Standalone file
     * @throws IOException if the API call fails or the response can't be read
     */
    private void fetchDescription() throws IOException {

        String cached = descriptionCache.getDescription(bookDetailForm.getIsbn());
        if (cached != null) {
            bookDetailForm.setDescription(cached);
            return;
        }

        String urlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + bookDetailForm.getIsbn() + "&key=" + System.getProperty("google.books.api.key");
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("GET");

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                while((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            JsonObject jsonResponse = Json.createReader(
                new java.io.StringReader(response.toString())
            ).readObject();

            JsonArray items = jsonResponse.getJsonArray("items");

            if (items != null && !items.isEmpty()) {
                JsonObject volumeInfo = items.getJsonObject(0).getJsonObject("volumeInfo");
                if (volumeInfo.containsKey("description")) {
                    String description = volumeInfo.getString("description");
                    bookDetailForm.setDescription(description);
                    descriptionCache.putDescription(bookDetailForm.getIsbn(), description);
                }
            }
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Loads the book details for the requested ISBN.
     * Triggered by f:viewAction after view parameters have been injected,
     * ensuring the ISBN is available before the book is fetched.
     * Description fetch failures are logged
     */
    public void init() {
        if (bookDetailForm.getIsbn() == null) {
            return;
        }
        bookDetailForm.setBook(bookManager.findByIsbn(bookDetailForm.getIsbn()));
        
        try {
            fetchDescription();
        } catch (IOException e) {
            LOG.warn("Failed to fetch book description for ISBN {}", bookDetailForm.getIsbn(), e);
            bookDetailForm.setDescription(null);
        }
    }
}
