package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.service.ReaderServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ReaderFindById implements ReaderSearch {
    @Override
    public List<Reader> search(String query) {
        return new ReaderServiceImpl().get().stream()
                .filter(reader -> query.equals(reader.getId().toString()))
                .collect(Collectors.toList());
    }
}
