package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.dao.AuthorDaoImpl;
import com.pszumanski.libraryregister.dao.BookDaoImpl;

import java.util.Arrays;
import java.util.List;

public class BookFindByAuthorName implements BookSearch {
    @Override
    public List<Book> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        List<Author> authors = new AuthorDaoImpl().get().stream()
                .filter(author -> {
                    String authorName = author.getName().toLowerCase();
                    for (String word: queries) {
                        if (!authorName.contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();

        List<Integer> authorIds = authors.stream()
                .map(author -> author.getId())
                .toList();


        return new BookDaoImpl().get().stream()
                .filter(book -> authorIds.contains(book.getAuthorId()))
                .toList();
    }
}
