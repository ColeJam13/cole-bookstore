package com.ksm.bookstore.provider;

import javax.faces.convert.FacesConverter;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.enterprise.inject.spi.CDI;

import com.ksm.bookstore.jpa.Author;
import com.ksm.bookstore.dao.AuthorManager;

@ApplicationScoped
@FacesConverter(value = "authorConverter", managed = true)
public class AuthorConverter implements Converter<Author>{

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
        return CDI.current().select(AuthorManager.class).get().findById(Long.valueOf(value));
    }
}
