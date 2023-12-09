package com.pszumanski.libraryregister.managers.dataManagers;

import com.pszumanski.libraryregister.repositories.AuthorRepository;
import com.pszumanski.libraryregister.repositories.BookRepository;
import com.pszumanski.libraryregister.repositories.ReaderRepository;

public interface FileManagerService {

    void loadDatabase();

    void createNew();

    void saveDatabase();

    void setRepos(AuthorRepository authorRepository, BookRepository bookRepository, ReaderRepository readerRepository);

}
