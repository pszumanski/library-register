package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.managers.BookManager;

import java.util.List;
import java.util.stream.Collectors;

public class BookFindByAuthorId implements BookSearch {
    @Override
    public List<Book> search(String query) {
        return new BookManager().get().stream()
                .filter(book -> query.equals(book.getAuthorId().toString()))
                .collect(Collectors.toList());
    }
}
