package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.service.BookServiceImpl;

import java.util.Arrays;
import java.util.List;

public class BookFindByTitle implements BookSearch {
    @Override
    public List<Book> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();
        return new BookServiceImpl().get().stream()
                .filter(book -> {
                    String bookTitle = book.getTitle().toLowerCase();
                    for (String word: queries) {
                        if (!bookTitle.contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
    }
}
