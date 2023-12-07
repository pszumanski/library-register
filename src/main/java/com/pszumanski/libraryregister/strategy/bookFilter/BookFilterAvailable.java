package com.pszumanski.libraryregister.strategy.bookFilter;

import com.pszumanski.libraryregister.data.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookFilterAvailable implements BookFilter {
    @Override
    public List<Book> filter(List<Book> booksToFilter) {
        return booksToFilter.stream()
                .filter(book -> book.getCurrentReaderId() == null)
                .collect(Collectors.toList());
    }
}
