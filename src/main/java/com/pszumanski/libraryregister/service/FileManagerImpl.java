package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.data.model.Author;
import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.data.model.Reader;
import com.pszumanski.libraryregister.data.repository.AuthorRepository;
import com.pszumanski.libraryregister.data.repository.BookRepository;
import com.pszumanski.libraryregister.data.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;

public class FileManagerImpl implements FileManager {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private ReaderRepository readerRepository;
    private final AuthorService authorManager;
    private final BookService bookManager;
    private final ReaderService readerManager;

    private static FileManagerImpl fileManager;

    public static FileManagerImpl getInstance() {
        if (fileManager == null) {
            fileManager = new FileManagerImpl();
        }

        return fileManager;
    }

    public void setRepos(AuthorRepository authorRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    private FileManagerImpl() {
        this.authorManager = new AuthorServiceImpl();
        this.bookManager = new BookServiceImpl();
        this.readerManager = new ReaderServiceImpl();
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
