package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.managers.dataManagers.BookManager;

import java.util.List;
import java.util.stream.Collectors;

public class BookFindByReaderId implements BookSearch {
    @Override
    public List<Book> search(String query) {
        return new BookManager().get().stream()
                .filter(book -> book.getCurrentReaderId() != null)
                .filter(book -> query.equals(book.getCurrentReaderId().toString()))
                .collect(Collectors.toList());
    }
}
