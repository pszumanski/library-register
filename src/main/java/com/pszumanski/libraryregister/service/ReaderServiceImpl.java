package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByReaderId;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;
import lombok.Getter;

import java.util.List;

@Getter
public class ReaderServiceImpl implements ReaderService {

    private static List<Reader> readers;
    private ReaderSearch readerSearch;

    @Override
    public List<Reader> get() {
        return ReaderServiceImpl.readers;
    }

    @Override
    public void add(Reader reader) {
        readers.add(reader);
    }

    @Override
    public void remove(Reader reader) {
        readers.remove(reader);
    }

    @Override
    public List<Reader> search(String query) {
        return readerSearch.search(query);
    }

    @Override
    public void setSearch(ReaderSearch readerSearch) {
        this.readerSearch = readerSearch;
    }

    @Override
    public void load(List<Reader> readers) {
        ReaderServiceImpl.readers = readers;
    }

    @Override
    public List<Book> fetchBooks(Reader reader) {
        BookServiceImpl bookManager = new BookServiceImpl();
        bookManager.setSearch(new BookFindByReaderId());
        return bookManager.search(reader.getId().toString());
    }
}
