package com.pszumanski.libraryregister.managers.inputManagers.validators;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManagerService;
import com.pszumanski.libraryregister.managers.dataManagers.TimeManager;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindById;

import java.util.Map;
import java.util.NoSuchElementException;

public class BookValidator implements ValidatorService {
    @Override
    public boolean validate(Map<String, String> fields) {
        if (fields.get("title").isEmpty()) {
            fields.put("title", "EMPTY");
        }

        // Check if author provide and exists in db
        if (fields.get("authorId").isEmpty()) {
            fields.put("bornDate", "EMPTY");
        } else {
            AuthorManagerService authorManager = new AuthorManager();
            authorManager.setSearch(new AuthorFindById());
            try {
                Author author = authorManager.search(fields.get("authorId")).getFirst();
            } catch (NoSuchElementException ex) {
                fields.put("authorId", "INCORRECT");
            }
        }
        if (fields.get("publisher").isEmpty()) {
            fields.put("publisher", "EMPTY");
        }
        // Check if publish year valid
        if (fields.get("publishYear").isEmpty()) {
            fields.put("publisherYear", "EMPTY");
        } else {
            try {
                int currentYear = TimeManager.getInstance().getDate().getYear();
                if (Integer.parseInt(fields.get("publishYear")) > currentYear) {
                    fields.put("publishYear", "INCORRECT");
                }
            } catch (Exception ex) {
                fields.put("publishYear", "INCORRECT");
            }
        }
        if (fields.get("isbn").isEmpty()) {
            fields.put("isbn", "EMPTY");
        }
        if (fields.get("genre").isEmpty()) {
            fields.put("genre", "EMPTY");
        }
        if (fields.get("language").isEmpty()) {
            fields.put("language", "EMPTY");
        }

        // Check if all data is correct
        for (String value: fields.values()) {
            if (value.equals("EMPTY") || value.equals("INCORRECT") || value.equals("ALREADY_EXISTS")) {
                return false;
            }
        }

        return true;
    }
}
