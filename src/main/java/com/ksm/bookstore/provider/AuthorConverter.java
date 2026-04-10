package com.ksm.bookstore.provider;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.dao.AuthorManager;

@FacesConverter(value = "authorConverter", managed = true)
public class AuthorConverter implements Converter<Author>{

    @Inject
    private AuthorManager authorManager;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Author author) {
        if (author == null) {
            return "";
        }
        return author.getAuthorId().toString();
    }

    @Override
    public Author getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return authorManager.findById(Long.valueOf(value));
    }
}
