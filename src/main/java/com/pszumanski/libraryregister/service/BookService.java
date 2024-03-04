package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    List<Book> get();

    void add(Book book);

    void remove(Book book);

    List<Book> search(String query);

    void setSearch(BookSearch bookSearch);

    void load(List<Book> books);

    boolean isLent(Book book);

    boolean isOverDue(Book book);

    List<String> fetchLanguages();

    List<String> fetchGenres();

    void lendBook(Book book, Reader reader, LocalDate date);
}
