package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.data.model.Author;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

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
        AuthorServiceImpl.authors = authors;
    }

    @Override
    public List<Author> get() {
        return AuthorServiceImpl.authors;
    }

    @Override
    public List<String> fetchTitles(Author author) {
        return new BookServiceImpl().get().stream()
                .filter(book -> author.getId().equals(book.getAuthorId()))
                .map(book -> book.getTitle())
                .distinct()
                .toList();
    }
}
