package com.pszumanski.libraryregister.data.factories;

import com.pszumanski.libraryregister.data.objects.Author;

import java.util.Map;

public interface AuthorFactoryService {

    Author create(Map<String, String> attributes);

}
