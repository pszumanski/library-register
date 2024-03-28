package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.dao.AuthorDaoImpl;
import com.pszumanski.libraryregister.dao.BookDaoImpl;

import java.util.*;

public class AuthorFindByTitle implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        List<Book> booksOfTitle = new BookDaoImpl().get().stream()
                .filter(book -> {
                    String bookTitle = book.getTitle().toLowerCase();
                    for (String word : queries) {
                        if (!bookTitle.contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();

        List<Author> authors = new ArrayList<>();
        booksOfTitle.forEach(book -> {
            Integer authorID = book.getAuthorId();

            authors.addAll(new AuthorDaoImpl().get().stream()
                    .filter(author -> authorID.equals(author.getId()))
                    .toList());

        });

        return authors.stream().distinct().toList();
    }
}
