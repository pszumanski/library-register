package com.pszumanski.libraryregister.managers;

import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;
import lombok.Getter;

import java.util.List;

@Getter
public class ReaderManager implements ReaderManagerService {

    private static List<Reader> readers;
    private static ReaderSearch readerSearch;

    @Override
    public List<Reader> get() {
        return ReaderManager.readers;
    }

    @Override
    public void add(Reader reader) {
        readers.add(reader);
    }

    @Override
    public void remove(Reader reader) {
        readers.remove(reader);
    }

    @Override
    public List<Reader> search(String query) {
        return readerSearch.search(query);
    }

    @Override
    public void setSearch(ReaderSearch readerSearch) {
        ReaderManager.readerSearch = readerSearch;
    }

    @Override
    public void load(List<Reader> readers) {
        ReaderManager.readers = readers;
    }
}
