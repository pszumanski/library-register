package com.pszumanski.libraryregister.data.factories;

import com.pszumanski.libraryregister.data.objects.Reader;

import java.util.Map;

public interface ReaderFactoryService {

    Reader create(Map<String, String> attributes);
}
