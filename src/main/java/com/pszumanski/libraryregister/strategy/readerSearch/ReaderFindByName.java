package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.dao.ReaderDaoImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderFindByName implements ReaderSearch {

    @Override
    public List<Reader> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        return new ReaderDaoImpl().get().stream()
                .filter(reader -> {
                    String readerName = reader.getName().toLowerCase();
                    for (String word: queries) {
                        if (!readerName.contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
