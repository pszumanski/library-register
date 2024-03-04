package com.pszumanski.libraryregister.strategy.readerFilter;

import com.pszumanski.libraryregister.data.model.Reader;

import java.util.List;

public interface ReaderFilter {

    List<Reader> filter(List<Reader> readersToFilter);

}
