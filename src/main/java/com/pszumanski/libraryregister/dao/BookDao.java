package com.pszumanski.libraryregister.dao;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;

import java.time.LocalDate;
import java.util.List;

public interface BookDao {

    List<Book> get();

    void add(Book book);

    void remove(Book book);

    List<Book> search(String query);

    void setSearch(BookSearch bookSearch);

    void load(List<Book> books);

    boolean isLent(Book book);

    boolean isOverDue(Book book);

    List<String> getLanguages();

    List<String> getGenres();

    void lendBook(Book book, Reader reader, LocalDate date);
}
