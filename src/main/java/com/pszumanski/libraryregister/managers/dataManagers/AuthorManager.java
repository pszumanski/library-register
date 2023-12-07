package com.pszumanski.libraryregister.managers.dataManagers;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;

import java.util.List;

public class AuthorManager implements AuthorManagerService {

    private static List<Author> authors;
    private static AuthorSearch authorSearch;

    @Override
    public void add(Author author) {
        //TODO: set author id
        authors.add(author);
    }

    @Override
    public void remove(Author author) {
        authors.remove(author);
    }

    @Override
    public List<Author> search(String query) {
        return authorSearch.search(query);
    }

    @Override
    public void setSearch(AuthorSearch authorSearch) {
        AuthorManager.authorSearch = authorSearch;
    }

    @Override
    public void load(List<Author> authors) {
        AuthorManager.authors = authors;
    }

    @Override
    public List<Author> get() {
        return AuthorManager.authors;
    }
}
