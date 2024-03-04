package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.model.Author;
import com.pszumanski.libraryregister.service.AuthorServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorFindById implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        return new AuthorServiceImpl().get().stream()
                .filter(author -> query.equals(author.getId().toString()))
                .collect(Collectors.toList());
    }
}
