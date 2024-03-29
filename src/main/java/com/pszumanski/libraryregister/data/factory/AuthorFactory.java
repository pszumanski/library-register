package com.pszumanski.libraryregister.data.factory;

import com.pszumanski.libraryregister.service.AuthorService;
import com.pszumanski.libraryregister.data.model.Author;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Map;

@RequiredArgsConstructor
public class AuthorFactory implements Factory<Author> {

    private final AuthorService authorManager;

    @Override
    public Author create(Map<String, String> attributes) {
        return new Author(
                (getMaxIndex() + 1),
                attributes.get("name"),
                attributes.get("bornDate"),
                attributes.get("deathDate")
                );
    }

    private Integer getMaxIndex() {
        return authorManager.get().stream()
                .map(author -> author.getId())
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}
