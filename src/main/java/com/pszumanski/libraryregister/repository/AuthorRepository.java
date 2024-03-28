package com.pszumanski.libraryregister.repository;

import com.pszumanski.libraryregister.data.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {}
