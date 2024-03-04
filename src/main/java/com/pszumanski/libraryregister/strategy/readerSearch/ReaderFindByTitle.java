package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.service.ReaderServiceImpl;
import com.pszumanski.libraryregister.service.ReaderService;

import java.util.Arrays;
import java.util.List;

public class ReaderFindByTitle implements ReaderSearch {
    @Override
    public List<Reader> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        ReaderService readerManager = new ReaderServiceImpl();

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
