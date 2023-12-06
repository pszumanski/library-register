package com.pszumanski.libraryregister.managers;

import com.pszumanski.libraryregister.repositories.AuthorRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileManager implements FileManageInterface {

    AuthorRepository repository;

    private AuthorManagerInterface authorManager;
    private BookManagerInterface bookManager;
    private ReaderManagerInterface readerManager;

    @Override
    public void loadDatabase() {
        authorManager.load(repository.findAll());
    }
}
