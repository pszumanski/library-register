package com.pszumanski.libraryregister.repositories;

import com.pszumanski.libraryregister.data.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
