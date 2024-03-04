package com.pszumanski.libraryregister.strategy.readerFilter;

import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.service.BookServiceImpl;
import com.pszumanski.libraryregister.service.BookService;
import com.pszumanski.libraryregister.service.ReaderServiceImpl;
import com.pszumanski.libraryregister.service.ReaderService;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderFindById;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ReaderFilterHasBooks implements ReaderFilter {

    @Override
    public List<Reader> filter(List<Reader> readersToFilter) {
        Set<Reader> filteredReaders = new TreeSet<>();
        BookService bookManager = new BookServiceImpl();
        ReaderService readerManager = new ReaderServiceImpl();
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
