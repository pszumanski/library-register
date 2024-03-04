package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.model.Author;
import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.service.AuthorServiceImpl;
import com.pszumanski.libraryregister.service.BookServiceImpl;

import java.util.*;

public class AuthorFindByTitle implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        List<Book> booksOfTitle = new BookServiceImpl().get().stream()
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

            authors.addAll(new AuthorServiceImpl().get().stream()
                    .filter(author -> authorID.equals(author.getId()))
                    .toList());

        });

        return authors.stream().distinct().toList();
    }
}
