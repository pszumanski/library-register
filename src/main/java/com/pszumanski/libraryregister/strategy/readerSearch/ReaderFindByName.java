package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderFindByName implements ReaderSearch {

    @Override
    public List<Reader> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();
        int numberOfWords = queries.size();

        return new ReaderManager().get().stream()
                .filter(reader -> {
                    int matches = 0;
                    List<String> readerName = Arrays.stream(reader.getName().toLowerCase().split(" ")).toList();
                    for (String word: queries) {
                        for (String readerSubName : readerName) {
                            if (readerSubName.contains(word)) {
                                matches++;
                                break;
                            }
                        }
                    }
                    return matches >= numberOfWords;
                })
                .collect(Collectors.toList());
    }
}
