package com.pszumanski.libraryregister.data.managers;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;

import java.util.List;

public interface ReaderManagerService {

    List<Reader> get();

    void add(Reader reader);

    void remove(Reader reader);

    List<Reader> search(String query);

    void setSearch(ReaderSearch readerSearch);

    void load(List<Reader> readers);

    List<Book> fetchBooks(Reader reader);

}
