package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;
import com.pszumanski.libraryregister.managers.dataManagers.BookManager;

import java.util.*;
import java.util.stream.Collectors;

public class AuthorFindByTitle implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        List<Book> booksOfTitle = new BookManager().get().stream()
                .filter(book -> query.equalsIgnoreCase(book.getTitle()))
                .toList();

        Set<Author> authors = new HashSet<>();
        AuthorManager authorManager = new AuthorManager();
        authorManager.setSearch(new AuthorFindById());

        booksOfTitle.forEach(book -> {
            Integer authorID = book.getAuthorId();
            authors.add(authorManager.search(authorID.toString()).get(0));
        });

        return authors.stream().toList();
    }
}
