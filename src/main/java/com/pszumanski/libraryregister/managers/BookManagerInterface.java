package com.pszumanski.libraryregister.managers;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;

import java.util.List;

public interface BookManagerInterface {

    void add(Book book);

    void remove(Book book);

    List<Book> search(String query);

    void setSearch(BookSearch bookSearch);
}
