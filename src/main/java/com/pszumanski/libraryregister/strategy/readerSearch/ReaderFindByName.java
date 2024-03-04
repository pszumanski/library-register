package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.service.ReaderServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderFindByName implements ReaderSearch {

    @Override
    public List<Reader> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        return new ReaderServiceImpl().get().stream()
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
