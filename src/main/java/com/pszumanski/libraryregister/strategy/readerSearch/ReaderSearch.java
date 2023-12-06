package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.Reader;

import java.util.List;

public interface ReaderSearch {

    List<Reader> search(String query);
}
