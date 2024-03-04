package com.pszumanski.libraryregister.strategy.bookFilter;

import com.pszumanski.libraryregister.data.model.Book;

import java.util.List;

public interface BookFilter {

    List<Book> filter(List<Book> booksToFilter);

}
