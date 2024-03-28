package com.pszumanski.libraryregister.dao;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;

import java.util.List;

public interface AuthorDao {

    List<Author> get();

    void add(Author author);

    void remove(Author author);

    List<Author> search(String query);

    void setSearch(AuthorSearch authorSearch);

    void load(List<Author> authors);

    List<String> getTitles(Author author);

}
