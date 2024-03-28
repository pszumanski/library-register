package com.pszumanski.libraryregister.dao;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;

import java.util.List;

public class AuthorDaoImpl implements AuthorDao {

    private static List<Author> authors;
    private AuthorSearch authorSearch;

    @Override
    public void add(Author author) {
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
        this.authorSearch = authorSearch;
    }

    @Override
    public void load(List<Author> authors) {
        AuthorDaoImpl.authors = authors;
    }

    @Override
    public List<Author> get() {
        return AuthorDaoImpl.authors;
    }

    @Override
    public List<String> getTitles(Author author) {
        return new BookDaoImpl().get().stream()
                .filter(book -> author.getId().equals(book.getAuthorId()))
                .map(book -> book.getTitle())
                .distinct()
                .toList();
    }
}
