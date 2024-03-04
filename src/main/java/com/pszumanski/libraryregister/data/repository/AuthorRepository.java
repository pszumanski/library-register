package com.pszumanski.libraryregister.data.repository;

import com.pszumanski.libraryregister.data.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {}
