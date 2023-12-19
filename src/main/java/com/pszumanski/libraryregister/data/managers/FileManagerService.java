package com.pszumanski.libraryregister.data.managers;

import com.pszumanski.libraryregister.data.repositories.AuthorRepository;
import com.pszumanski.libraryregister.data.repositories.BookRepository;
import com.pszumanski.libraryregister.data.repositories.ReaderRepository;

public interface FileManagerService {

    void loadDatabase();

    void createNew();

    void saveDatabase();

    void setRepos(AuthorRepository authorRepository, BookRepository bookRepository, ReaderRepository readerRepository);

}
