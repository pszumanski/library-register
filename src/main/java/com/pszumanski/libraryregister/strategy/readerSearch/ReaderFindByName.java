package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderFindByName implements ReaderSearch {

    @Override
    public List<Reader> search(String query) {
        List<String> queries = Arrays.stream(query.split(" ")).toList();

        return new ReaderManager().get().stream()
                .filter(reader -> {
                    List<String> authorName = Arrays.stream(reader.getName().toLowerCase().split(" ")).toList();
                    for (String word: queries) {
                        if (!authorName.contains(word.toLowerCase())) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
