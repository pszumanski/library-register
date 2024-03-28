package com.pszumanski.libraryregister.repository;

import com.pszumanski.libraryregister.data.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {}
