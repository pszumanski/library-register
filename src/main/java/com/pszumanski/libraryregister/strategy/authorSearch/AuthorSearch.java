package com.pszumanski.libraryregister.strategy.authorSearch;

import com.pszumanski.libraryregister.data.objects.Author;

import java.util.List;

public interface AuthorSearch {

    List<Author> search(String query);

}
