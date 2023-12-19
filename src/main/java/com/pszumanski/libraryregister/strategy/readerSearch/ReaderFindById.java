package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.data.managers.ReaderManager;

import java.util.List;
import java.util.stream.Collectors;

public class ReaderFindById implements ReaderSearch {
    @Override
    public List<Reader> search(String query) {
        return new ReaderManager().get().stream()
                .filter(reader -> query.equals(reader.getId().toString()))
                .collect(Collectors.toList());
    }
}
