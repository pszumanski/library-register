package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.managers.AuthorManager;
import com.pszumanski.libraryregister.data.managers.BookManager;

import java.util.*;

public class AuthorFindByTitle implements AuthorSearch {
    @Override
    public List<Author> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        List<Book> booksOfTitle = new BookManager().get().stream()
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

            authors.addAll(new AuthorManager().get().stream()
                    .filter(author -> authorID.equals(author.getId()))
                    .toList());

        });

        return authors.stream().distinct().toList();
    }
}
