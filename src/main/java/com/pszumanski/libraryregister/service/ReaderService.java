package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;

import java.util.List;

public interface ReaderService {

    List<Reader> get();

    void add(Reader reader);

    void remove(Reader reader);

    List<Reader> search(String query);

    void setSearch(ReaderSearch readerSearch);

    void load(List<Reader> readers);

    List<Book> fetchBooks(Reader reader);

}
