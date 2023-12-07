package com.pszumanski.libraryregister.managers.dataManagers;

import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;

import java.util.List;

public interface ReaderManagerService {

    List<Reader> get();

    void add(Reader reader);

    void remove(Reader reader);

    List<Reader> search(String query);

    void setSearch(ReaderSearch readerSearch);

    void load(List<Reader> readers);

}
