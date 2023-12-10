package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.managers.dataManagers.BookManager;

import java.util.Arrays;
import java.util.List;

public class BookFindByTitle implements BookSearch {
    @Override
    public List<Book> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();
        int numberOfWords = queries.size();
        return new BookManager().get().stream()
                .filter(book -> {
                    List<String> bookTitle = Arrays.stream(book.getTitle().toLowerCase().split(" ")).toList();
                    int matches = 0;
                    for (String word: queries) {
                        for (String bookSubTitle: bookTitle) {
                            if (bookSubTitle.contains(word)) {
                                matches++;
                                break;
                            }
                        }
                    }
                    return matches >= numberOfWords;
                })
                .toList();
    }
}
