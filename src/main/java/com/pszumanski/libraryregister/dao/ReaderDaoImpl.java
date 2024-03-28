package com.pszumanski.libraryregister.dao;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByReaderId;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;
import lombok.Getter;

import java.util.List;

@Getter
public class ReaderDaoImpl implements ReaderDao {

    private static List<Reader> readers;
    private ReaderSearch readerSearch;

    @Override
    public List<Reader> get() {
        return ReaderDaoImpl.readers;
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
        ReaderDaoImpl.readers = readers;
    }

    @Override
    public List<Book> getBooks(Reader reader) {
        BookDaoImpl bookManager = new BookDaoImpl();
        bookManager.setSearch(new BookFindByReaderId());
        return bookManager.search(reader.getId().toString());
    }
}
