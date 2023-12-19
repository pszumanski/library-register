package com.pszumanski.libraryregister.data.managers;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByReaderId;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderSearch;
import lombok.Getter;

import java.util.List;

@Getter
public class ReaderManager implements ReaderManagerService {

    private static List<Reader> readers;
    private static ReaderSearch readerSearch;

    @Override
    public List<Reader> get() {
        return ReaderManager.readers;
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
        ReaderManager.readerSearch = readerSearch;
    }

    @Override
    public void load(List<Reader> readers) {
        ReaderManager.readers = readers;
    }

    @Override
    public List<Book> fetchBooks(Reader reader) {
        BookManager bookManager = new BookManager();
        bookManager.setSearch(new BookFindByReaderId());
        return bookManager.search(reader.getId().toString());
    }
}
