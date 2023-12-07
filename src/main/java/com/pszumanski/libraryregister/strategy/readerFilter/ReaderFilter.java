package com.pszumanski.libraryregister.strategy.readerFilter;

import com.pszumanski.libraryregister.data.Reader;

import java.util.List;

public interface ReaderFilter {

    List<Reader> filter(List<Reader> readersToFilter);

}
