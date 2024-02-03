package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.data.managers.ReaderManager;
import com.pszumanski.libraryregister.data.managers.ReaderManagerService;

import java.util.Arrays;
import java.util.List;

public class ReaderFindByTitle implements ReaderSearch {
    @Override
    public List<Reader> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        ReaderManagerService readerManager = new ReaderManager();

        return readerManager.get().stream()
                .filter(reader -> !readerManager.fetchBooks(reader).isEmpty())
                .filter(reader -> {
                    for (Book book : readerManager.fetchBooks(reader)) {
                        String title = book.getTitle().toLowerCase();

                        for (String word: queries) {
                                if (!title.contains(word)) {
                                    return false;
                                }
                            }
                        return true;
                    }
                    return false;
                }).toList();
    }
}
