package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.dao.BookDaoImpl;

import java.util.List;
import java.util.stream.Collectors;

public class BookFindById implements BookSearch {
    @Override
    public List<Book> search(String query) {
        return new BookDaoImpl().get().stream()
                .filter(book -> query.equals(book.getId().toString()))
                .collect(Collectors.toList());
    }
}
