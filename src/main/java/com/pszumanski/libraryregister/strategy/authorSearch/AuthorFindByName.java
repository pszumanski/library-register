package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.dao.AuthorDaoImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorFindByName implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        return new AuthorDaoImpl().get().stream()
                .filter(author -> {
                    String authorName = author.getName().toLowerCase();
                    for (String word: queries) {
                        if (!authorName.contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
