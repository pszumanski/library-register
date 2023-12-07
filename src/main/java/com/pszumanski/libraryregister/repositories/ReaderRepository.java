package com.pszumanski.libraryregister.repositories;

import com.pszumanski.libraryregister.data.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {
}
