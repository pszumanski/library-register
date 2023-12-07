package com.pszumanski.libraryregister.managers;

import com.pszumanski.libraryregister.repositories.AuthorRepository;
import com.pszumanski.libraryregister.repositories.BookRepository;
import com.pszumanski.libraryregister.repositories.ReaderRepository;
import lombok.AllArgsConstructor;


public class FileManager implements FileManagerService {

    private static AuthorRepository authorRepository;
    private static BookRepository bookRepository;
    private static ReaderRepository readerRepository;

    private final AuthorManagerService authorManager;
    private final BookManagerService bookManager;
    private final ReaderManagerService readerManager;

    public FileManager() {
        this.authorManager = new AuthorManager();
        this.bookManager = new BookManager();
        this.readerManager = new ReaderManager();
    }

    public FileManager(AuthorRepository authorRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        FileManager.authorRepository = authorRepository;
        FileManager.bookRepository = bookRepository;
        FileManager.readerRepository = readerRepository;
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
    public void saveDatabase() {
        authorRepository.deleteAll();
        authorRepository.saveAll(authorManager.get());

        bookRepository.deleteAll();
        bookRepository.saveAll(bookManager.get());

        readerRepository.deleteAll();
        readerRepository.saveAll(readerManager.get());
    }
}
