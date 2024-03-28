package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.dao.AuthorDaoImpl;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorFindById implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        return new AuthorDaoImpl().get().stream()
                .filter(author -> query.equals(author.getId().toString()))
                .collect(Collectors.toList());
    }
}
