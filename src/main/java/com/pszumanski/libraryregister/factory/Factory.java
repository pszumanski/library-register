package com.pszumanski.libraryregister.factory;

import java.util.Map;

public interface Factory<T> {

    T create(Map<String, String> attributes);
}
