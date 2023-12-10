package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManagerService;
import com.pszumanski.libraryregister.managers.dataManagers.BookManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookFindByAuthorName implements BookSearch {
    @Override
    public List<Book> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();
        AuthorManagerService authorManager = new AuthorManager();
        int numberOfWords = queries.size();

        List<Author> authors = authorManager.get().stream()
                .filter(author -> {
                    List<String> authorName = Arrays.stream(author.getName().toLowerCase().split(" ")).toList();
                    int matches = 0;
                    for (String word: queries) {
                        for (String authorSubName : authorName) {
                            if (authorSubName.contains(word)) {
                                matches++;
                                break;
                            }
                        }
                    }
                    return matches >= numberOfWords;
                })
                .toList();
        List<Integer> authorIds = new ArrayList<>();
        authors.forEach(author -> authorIds.add(author.getId()));


        return new BookManager().get().stream()
                .filter(book -> authorIds.contains(book.getAuthorId()))
                .toList();
    }
}
