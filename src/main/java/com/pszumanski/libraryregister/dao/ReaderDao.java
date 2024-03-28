package com.pszumanski.libraryregister.dao;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;

import java.util.List;

public interface ReaderDao {

    List<Reader> get();

    void add(Reader reader);

    void remove(Reader reader);

    List<Reader> search(String query);

    void setSearch(ReaderSearch readerSearch);

    void load(List<Reader> readers);

    List<Book> getBooks(Reader reader);

}
