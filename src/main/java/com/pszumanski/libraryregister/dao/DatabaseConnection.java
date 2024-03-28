package com.pszumanski.libraryregister.dao;

import com.pszumanski.libraryregister.repository.AuthorRepository;
import com.pszumanski.libraryregister.repository.BookRepository;
import com.pszumanski.libraryregister.repository.ReaderRepository;

public interface DatabaseConnection {

    void loadDatabase();

    void createNew();

    void saveDatabase();

    void setRepos(AuthorRepository authorRepository, BookRepository bookRepository, ReaderRepository readerRepository);

}
