package com.pszumanski.libraryregister.data.managers;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;

import java.time.LocalDate;
import java.util.List;

public interface BookManagerService {

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
