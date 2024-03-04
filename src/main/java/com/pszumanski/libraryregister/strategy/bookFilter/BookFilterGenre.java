package com.pszumanski.libraryregister.strategy.bookFilter;

import com.pszumanski.libraryregister.data.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookFilterGenre implements BookFilter {

    String genre;

    public BookFilterGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public List<Book> filter(List<Book> booksToFilter) {
        return booksToFilter.stream()
                .filter(book -> book.getGenre().equals(genre))
                .collect(Collectors.toList());
    }
}
