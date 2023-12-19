package com.pszumanski.libraryregister.data.managers;

import com.pszumanski.libraryregister.data.repositories.AuthorRepository;
import com.pszumanski.libraryregister.data.repositories.BookRepository;
import com.pszumanski.libraryregister.data.repositories.ReaderRepository;

import java.util.ArrayList;

public class FileManager implements FileManagerService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private ReaderRepository readerRepository;
    private final AuthorManagerService authorManager;
    private final BookManagerService bookManager;
    private final ReaderManagerService readerManager;

    private static FileManager fileManager;

    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }

        return fileManager;
    }

    public void setRepos(AuthorRepository authorRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    private FileManager() {
        this.authorManager = new AuthorManager();
        this.bookManager = new BookManager();
        this.readerManager = new ReaderManager();
    }

    @Override
    public void loadDatabase() {
        authorManager.load(authorRepository.findAll());
        bookManager.load(bookRepository.findAll());
        readerManager.load(readerRepository.findAll());
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
