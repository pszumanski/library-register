package com.pszumanski.libraryregister.data.factory;

import java.util.Map;

public interface Factory<T> {

    T create(Map<String, String> attributes);
}
