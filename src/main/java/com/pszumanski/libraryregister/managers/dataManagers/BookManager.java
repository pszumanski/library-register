package com.pszumanski.libraryregister.managers.dataManagers;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;
import lombok.Getter;

import java.util.List;

@Getter
public class BookManager implements BookManagerService {

    private static List<Book> books;
    private static BookSearch bookSearch;

    @Override
    public List<Book> get() {
        return BookManager.books;
    }

    @Override
    public void add(Book book) {
        //TODO: add book id
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

    @Override
    public void load(List<Book> books) {
        BookManager.books = books;
    }
}
