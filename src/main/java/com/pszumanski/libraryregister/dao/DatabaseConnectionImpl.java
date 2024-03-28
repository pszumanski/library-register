package com.pszumanski.libraryregister.dao;

import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.repository.AuthorRepository;
import com.pszumanski.libraryregister.repository.BookRepository;
import com.pszumanski.libraryregister.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnectionImpl implements DatabaseConnection {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private ReaderRepository readerRepository;
    private final AuthorDao authorManager;
    private final BookDao bookManager;
    private final ReaderDao readerManager;

    private static DatabaseConnectionImpl fileManager;

    public static DatabaseConnectionImpl getInstance() {
        if (fileManager == null) {
            fileManager = new DatabaseConnectionImpl();
        }

        return fileManager;
    }

    public void setRepos(AuthorRepository authorRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    private DatabaseConnectionImpl() {
        this.authorManager = new AuthorDaoImpl();
        this.bookManager = new BookDaoImpl();
        this.readerManager = new ReaderDaoImpl();
    }

    @Override
    public void loadDatabase() {
        authorManager.load((List<Author>) authorRepository.findAll());
        bookManager.load((List<Book>) bookRepository.findAll());
        readerManager.load((List<Reader>) readerRepository.findAll());
    }

    @Override
    public void createNew() {
        authorManager.load(new ArrayList<>());
        bookManager.load(new ArrayList<>());
        readerManager.load(new ArrayList<>());
    }

    @Override
    public void saveDatabase() {
        authorRepository.deleteAll();
        authorRepository.saveAll(authorManager.get());

        bookRepository.deleteAll();
        bookRepository.saveAll(bookManager.get());

        readerRepository.deleteAll();
        readerRepository.saveAll(readerManager.get());
    }
}
