package com.pszumanski.libraryregister.strategy.readerFilter;

import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.dao.BookDaoImpl;
import com.pszumanski.libraryregister.dao.BookDao;
import com.pszumanski.libraryregister.dao.ReaderDaoImpl;
import com.pszumanski.libraryregister.dao.ReaderDao;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderFindById;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ReaderFilterHasBooks implements ReaderFilter {

    @Override
    public List<Reader> filter(List<Reader> readersToFilter) {
        Set<Reader> filteredReaders = new TreeSet<>();
        BookDao bookManager = new BookDaoImpl();
        ReaderDao readerManager = new ReaderDaoImpl();
        readerManager.setSearch(new ReaderFindById());
        bookManager.get().stream()
                .filter(book -> book.getCurrentReaderId() != null)
                .forEach(book -> {
                    Reader reader = readerManager.search(book.getCurrentReaderId().toString()).getFirst();
                    if (readersToFilter.contains(reader)) {
                        filteredReaders.add(reader);
                    }
                });
        return filteredReaders.stream().toList();
    }
}
