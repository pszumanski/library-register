package com.pszumanski.libraryregister.strategy.bookFilter;

import com.pszumanski.libraryregister.data.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookFilterLanguage implements BookFilter {

    String language;

    public BookFilterLanguage(String language) {
        this.language = language;
    }

    @Override
    public List<Book> filter(List<Book> booksToFilter) {
        return booksToFilter.stream()
                .filter(book -> book.getLanguage().equals(language))
                .collect(Collectors.toList());
    }

}
