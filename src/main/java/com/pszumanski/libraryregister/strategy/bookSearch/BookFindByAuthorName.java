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
        List<String> queries = Arrays.stream(query.split(" ")).toList();
        AuthorManagerService authorManager = new AuthorManager();

        List<Author> authors = authorManager.get().stream()
                .filter(author -> {
                    List<String> authorName = Arrays.stream(author.getName().toLowerCase().split(" ")).toList();
                    for (String word: queries) {
                        if (!authorName.contains(word.toLowerCase())) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
        List<Integer> authorIds = new ArrayList<>();
        authors.forEach(author -> authorIds.add(author.getId()));


        return new BookManager().get().stream()
                .filter(book -> authorIds.contains(book.getAuthorId()))
                .toList();
    }
}
