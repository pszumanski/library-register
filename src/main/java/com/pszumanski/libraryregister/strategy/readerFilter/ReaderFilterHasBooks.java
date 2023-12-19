package com.pszumanski.libraryregister.strategy.readerFilter;

import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.data.managers.BookManager;
import com.pszumanski.libraryregister.data.managers.BookManagerService;
import com.pszumanski.libraryregister.data.managers.ReaderManager;
import com.pszumanski.libraryregister.data.managers.ReaderManagerService;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderFindById;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ReaderFilterHasBooks implements ReaderFilter {

    @Override
    public List<Reader> filter(List<Reader> readersToFilter) {
        Set<Reader> filteredReaders = new TreeSet<>();
        BookManagerService bookManager = new BookManager();
        ReaderManagerService readerManager = new ReaderManager();
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
