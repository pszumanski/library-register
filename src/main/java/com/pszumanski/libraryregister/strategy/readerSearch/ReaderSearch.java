package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.model.Reader;

import java.util.List;

public interface ReaderSearch {

    List<Reader> search(String query);
}
