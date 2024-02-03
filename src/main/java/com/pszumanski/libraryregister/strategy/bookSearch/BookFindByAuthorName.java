package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.managers.AuthorManager;
import com.pszumanski.libraryregister.data.managers.AuthorManagerService;
import com.pszumanski.libraryregister.data.managers.BookManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookFindByAuthorName implements BookSearch {
    @Override
    public List<Book> search(String query) {
        List<String> queries = Arrays.stream(query.toLowerCase().split(" ")).toList();

        List<Author> authors = new AuthorManager().get().stream()
                .filter(author -> {
                    String authorName = author.getName().toLowerCase();
                    for (String word: queries) {
                        if (!authorName.contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();

        List<Integer> authorIds = authors.stream()
                .map(author -> author.getId())
                .toList();


        return new BookManager().get().stream()
                .filter(book -> authorIds.contains(book.getAuthorId()))
                .toList();
    }
}
