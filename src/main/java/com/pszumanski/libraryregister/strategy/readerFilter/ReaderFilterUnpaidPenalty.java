package com.pszumanski.libraryregister.strategy.readerFilter;

import com.pszumanski.libraryregister.data.objects.Reader;

import java.util.List;

public class ReaderFilterUnpaidPenalty implements ReaderFilter {

    @Override
    public List<Reader> filter(List<Reader> readersToFilter) {
        return readersToFilter.stream()
                .filter(reader -> reader.getPenalty() > 0)
                .toList();
    }
}
