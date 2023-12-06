package com.pszumanski.libraryregister.managers;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;

import java.util.List;

public interface ReaderManagerInterface {

    void add(Reader reader);

    void remove(Reader reader);

    List<Reader> search(String query);

    void setSearch(ReaderSearch readerSearch);

}
