package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorFindByName implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();
        int numberOfWords = queries.size();

        return new AuthorManager().get().stream()
                .filter(author -> {
                    List<String> authorName = Arrays.stream(author.getName().toLowerCase().split(" ")).toList();
                    int matches = 0;
                    for (String word: queries) {
                        for (String authorSubName: authorName) {
                            if (authorSubName.contains(word)) {
                                matches++;
                                break;
                            }
                        }
                    }
                    return matches >= numberOfWords;
                })
                .collect(Collectors.toList());
    }
}
