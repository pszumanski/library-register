package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BookServiceImpl implements BookService {

    private static List<Book> books;
    private BookSearch bookSearch;

    @Override
    public List<Book> get() {
        return BookServiceImpl.books;
    }

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
        this.bookSearch = bookSearch;
    }

    @Override
    public void load(List<Book> books) {
        BookServiceImpl.books = books;
    }

    @Override
    public boolean isLent(Book book) {
        return book.getCurrentReaderId() != null;
    }

    @Override
    public boolean isOverDue(Book book) {
        return TimeServiceImpl.getInstance().isBefore(book.getDeadline());
    }

    @Override
    public List<String> fetchLanguages() {
        return books.stream()
                .map(book -> book.getLanguage())
                .distinct()
                .toList();
    }

    @Override
    public List<String> fetchGenres() {
        return books.stream()
                .map(book -> book.getGenre())
                .distinct()
                .toList();
    }

    @Override
    public void lendBook(Book book, Reader reader, LocalDate date) {
        book.setDeadline(date);
        book.setCurrentReaderId(reader.getId());
    }
}
