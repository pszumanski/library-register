package com.pszumanski.libraryregister.managers;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;
import lombok.Getter;

import java.util.List;

@Getter
public class BookManager implements BookManagerInterface {

    private static List<Book> books;
    private static BookSearch bookSearch;

    @Override
    public void add(Book book) {
        books.add(book);
    }

    @Override
    public void remove(Book book) {
        books.remove(book);
    }

    @Override
    public List<Book> search(String query) {
        return bookSearch.search(query);
    }

    @Override
    public void setSearch(BookSearch bookSearch) {
        BookManager.bookSearch = bookSearch;
    }
}
