package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.data.repository.AuthorRepository;
import com.pszumanski.libraryregister.data.repository.BookRepository;
import com.pszumanski.libraryregister.data.repository.ReaderRepository;

public interface FileManager {

    void loadDatabase();

    void createNew();

    void saveDatabase();

    void setRepos(AuthorRepository authorRepository, BookRepository bookRepository, ReaderRepository readerRepository);

}
