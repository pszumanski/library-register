package com.pszumanski.libraryregister.managers.inputManagers.factories;

import com.pszumanski.libraryregister.data.objects.Reader;

import java.util.Map;

public interface ReaderFactoryService {

    Reader create(Map<String, String> attributes);
}
