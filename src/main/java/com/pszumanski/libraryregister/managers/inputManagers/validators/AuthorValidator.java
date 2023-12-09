package com.pszumanski.libraryregister.managers.inputManagers.validators;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManagerService;
import com.pszumanski.libraryregister.managers.dataManagers.TimeManager;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindByName;

import java.util.List;
import java.util.Map;

public class AuthorValidator implements ValidatorService {
    @Override
    public boolean validate(Map<String, String> fields) {

        if (fields.get("name").isEmpty()) {
            fields.put("name", "EMPTY");
        } else {
            // Check if author is already in db
            AuthorManagerService authorManager = new AuthorManager();
            authorManager.setSearch(new AuthorFindByName());
            List<Author> author = authorManager.search(fields.get("name"));
            if (!author.isEmpty()) {
                fields.put("name", "ALREADY_EXISTS");
            }
        }
        if (fields.get("bornDate").isEmpty()) {
            fields.put("bornDate", "-");
        }
        if (fields.get("deathDate").isEmpty()) {
            fields.put("deathDate", "-");
        }

        try {
            Integer bornDate = null;
            Integer deathDate = null;

            // Check if born and death dates provided
            if (!fields.get("bornDate").equals("-")) {
                bornDate = Integer.parseInt(fields.get("bornDate"));
            }
            if (!fields.get("deathDate").equals("-")) {
                deathDate = Integer.parseInt(fields.get("deathDate"));
            }

            // Get current year
            TimeManager timeManager = TimeManager.getInstance();
            int currentYear = timeManager.getDate().getYear();

            // Check if born and death dates are valid
            if (bornDate != null && bornDate > currentYear) {
                fields.put("bornDate", "INCORRECT");
            }

            if (deathDate != null && deathDate > currentYear) {
                fields.put("deathDate", "INCORRECT");
            }

            if (bornDate != null && deathDate != null) {
                if (bornDate > deathDate) {
                    fields.put("bornDate", "INCORRECT");
                    fields.put("deathDate", "INCORRECT");
                }
            }
        } catch (Exception ex) {
            // Dates are not numbers
            fields.put("bornDate", "INCORRECT");
            fields.put("deathDate", "INCORRECT");
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
