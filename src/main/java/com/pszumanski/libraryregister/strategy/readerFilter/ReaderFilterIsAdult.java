package com.pszumanski.libraryregister.strategy.readerFilter;

import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.service.TimeService;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReaderFilterIsAdult implements ReaderFilter {

    @Override
    public List<Reader> filter(List<Reader> readersToFilter) {
        return readersToFilter.stream()
                .filter(reader -> (ChronoUnit.YEARS.between(reader.getBornDate(), TimeService.getInstance().getDate()) >= 18))
                .toList();
    }
}
