package com.pszumanski.libraryregister.strategy.readerSearch;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.dao.ReaderDaoImpl;
import com.pszumanski.libraryregister.dao.ReaderDao;

import java.util.Arrays;
import java.util.List;

public class ReaderFindByTitle implements ReaderSearch {
    @Override
    public List<Reader> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        ReaderDao readerManager = new ReaderDaoImpl();

        return readerManager.get().stream()
                .filter(reader -> !readerManager.getBooks(reader).isEmpty())
                .filter(reader -> {
                    for (Book book : readerManager.getBooks(reader)) {
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
