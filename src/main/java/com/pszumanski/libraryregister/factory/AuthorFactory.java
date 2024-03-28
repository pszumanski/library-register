package com.pszumanski.libraryregister.factory;

import com.pszumanski.libraryregister.dao.AuthorDao;
import com.pszumanski.libraryregister.data.Author;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Map;

@RequiredArgsConstructor
public class AuthorFactory implements Factory<Author> {

    private final AuthorDao authorManager;

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
