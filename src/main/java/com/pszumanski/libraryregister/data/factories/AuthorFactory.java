package com.pszumanski.libraryregister.data.factories;

import com.pszumanski.libraryregister.data.managers.AuthorManager;
import com.pszumanski.libraryregister.data.managers.AuthorManagerService;
import com.pszumanski.libraryregister.data.objects.Author;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return authorManager.get().stream()
                .map(author -> author.getId())
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}
