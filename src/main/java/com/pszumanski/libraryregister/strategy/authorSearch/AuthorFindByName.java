package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorFindByName implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        List<String> queries = Arrays.stream(query.split(" ")).toList();

        return new AuthorManager().get().stream()
                .filter(author -> {
                    List<String> authorName = Arrays.stream(author.getName().toLowerCase().split(" ")).toList();
                    for (String word: queries) {
                        if (!authorName.contains(word.toLowerCase())) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
