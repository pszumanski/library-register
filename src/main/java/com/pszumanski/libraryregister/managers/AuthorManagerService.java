package com.pszumanski.libraryregister.managers;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;

import java.util.List;

public interface AuthorManagerService {

    List<Author> get();

    void add(Author author);

    void remove(Author author);

    List<Author> search(String query);

    void setSearch(AuthorSearch authorSearch);

    void load(List<Author> authors);

}
