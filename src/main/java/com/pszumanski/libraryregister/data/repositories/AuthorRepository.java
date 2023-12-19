package com.pszumanski.libraryregister.data.repositories;

import com.pszumanski.libraryregister.data.objects.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
