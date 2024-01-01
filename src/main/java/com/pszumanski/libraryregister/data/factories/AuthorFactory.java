package com.pszumanski.libraryregister.data.factories;

import com.pszumanski.libraryregister.data.managers.AuthorManager;
import com.pszumanski.libraryregister.data.managers.AuthorManagerService;
import com.pszumanski.libraryregister.data.objects.Author;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class AuthorFactory implements AuthorFactoryService {

    AuthorManagerService authorManager;

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
        List<Author> authors = authorManager.get().stream()
                .sorted((author1, author2) -> author2.getId() - author1.getId())
                .toList();
        return authors.isEmpty() ? 0 : authors.getFirst().getId();
    }
}
