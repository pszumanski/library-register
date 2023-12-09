package com.pszumanski.libraryregister.strategy.bookSearch;

import com.pszumanski.libraryregister.data.objects.Book;

import java.util.List;

public interface BookSearch {

    List<Book> search(String query);
}
