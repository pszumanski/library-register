package com.pszumanski.libraryregister.data.repositories;

import com.pszumanski.libraryregister.data.objects.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {}
