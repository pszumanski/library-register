package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManager;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManagerService;

import java.util.Arrays;
import java.util.List;

public class ReaderFindByTitle implements ReaderSearch {
    @Override
    public List<Reader> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();
        int numberOfWords = queries.size();

        ReaderManagerService readerManager = new ReaderManager();

        return readerManager.get().stream()
                .filter(reader -> !readerManager.fetchBooks(reader).isEmpty())
                .filter(reader -> {
                    for (Book book : readerManager.fetchBooks(reader)) {
                        List<String> title = List.of(book.getTitle().toLowerCase().split(" "));
                        int matches = 0;

                        for (String word: queries) {
                            for (String subTitle: title) {
                                if (subTitle.contains(word)) {
                                    matches++;
                                    break;
                                }
                            }
                        }
                        if (matches >= numberOfWords) {
                            return true;
                        }
                    }
                    return false;
                }).toList();
    }
}
