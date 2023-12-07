package com.pszumanski.libraryregister.managers.dataManagers;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;

import java.util.List;

public interface BookManagerService {

    List<Book> get();

    void add(Book book);

    void remove(Book book);

    List<Book> search(String query);

    void setSearch(BookSearch bookSearch);

    void load(List<Book> books);
}
