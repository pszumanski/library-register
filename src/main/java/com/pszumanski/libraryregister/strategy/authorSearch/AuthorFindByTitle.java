package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;
import com.pszumanski.libraryregister.managers.dataManagers.BookManager;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class AuthorFindByTitle implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        List<Book> booksOfTitle = new BookManager().get().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()))
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
