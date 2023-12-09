package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.managers.dataManagers.BookManager;

import java.util.List;

public class BookFindByTitle implements BookSearch {
    @Override
    public List<Book> search(String query) {
        return new BookManager().get().stream()
                .filter(book -> query.equalsIgnoreCase(book.getTitle()))
                .toList();
    }
}
