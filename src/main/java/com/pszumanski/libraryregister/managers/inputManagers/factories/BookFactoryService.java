package com.pszumanski.libraryregister.managers.inputManagers.factories;

import com.pszumanski.libraryregister.data.Book;

import java.util.Map;

public interface BookFactoryService {

    Book create(Map<String, String> attributes);
}
