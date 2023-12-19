package com.pszumanski.libraryregister.data.factories;

import com.pszumanski.libraryregister.data.objects.Book;

import java.util.Map;

public interface BookFactoryService {

    Book create(Map<String, String> attributes);
}
