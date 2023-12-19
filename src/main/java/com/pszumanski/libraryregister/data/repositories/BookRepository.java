package com.pszumanski.libraryregister.data.repositories;

import com.pszumanski.libraryregister.data.objects.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
