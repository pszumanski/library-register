package com.pszumanski.libraryregister.strategy.readerFilter;

import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.BookManager;
import com.pszumanski.libraryregister.managers.dataManagers.BookManagerService;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManager;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManagerService;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderFindById;

import java.util.ArrayList;
import java.util.List;

public class ReaderFilterHasNoBooks implements ReaderFilter {

    @Override
    public List<Reader> filter(List<Reader> readersToFilter) {
        List<Reader> filteredReaders = new ArrayList<>();
        BookManagerService bookManager = new BookManager();
        ReaderManagerService readerManager = new ReaderManager();
        readerManager.setSearch(new ReaderFindById());
        bookManager.get().stream()
                .filter(book -> book.getCurrentReaderId() != null)
                .forEach(book -> {
                    Reader reader = readerManager.search(book.getCurrentReaderId().toString()).getFirst();
                    filteredReaders.add(reader);
                });
        readersToFilter.removeAll(filteredReaders);
        return readersToFilter;
    }
}
