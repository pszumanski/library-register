package com.pszumanski.libraryregister.managers.inputManagers.factories;

import com.pszumanski.libraryregister.data.Author;

import java.util.Map;

public interface AuthorFactoryService {

    Author create(Map<String, String> attributes);

}
