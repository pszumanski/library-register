package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.managers.BookManager;

import java.util.Arrays;
import java.util.List;

public class BookFindByTitle implements BookSearch {
    @Override
    public List<Book> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();
        return new BookManager().get().stream()
                .filter(book -> {
                    String bookTitle = book.getTitle().toLowerCase();
                    for (String word: queries) {
                        if (!bookTitle.contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
    }
}
