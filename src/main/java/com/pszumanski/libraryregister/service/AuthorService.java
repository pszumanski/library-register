package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.data.model.Author;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;

import java.util.List;

public interface AuthorService {

    List<Author> get();

    void add(Author author);

    void remove(Author author);

    List<Author> search(String query);

    void setSearch(AuthorSearch authorSearch);

    void load(List<Author> authors);

    List<String> fetchTitles(Author author);

}
