package com.pszumanski.libraryregister.managers.inputManagers.factories;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManagerService;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class AuthorFactory implements AuthorFactoryService {

    AuthorManagerService authorMangerr;

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
        AuthorManager authorManager = new AuthorManager();
        return authorManager.get().stream()
                .sorted((author1, author2) -> {
                    return author2.getId() - author1.getId();
                })
                .toList()
                .getFirst()
                .getId();
    }
}
