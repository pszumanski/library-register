package com.pszumanski.libraryregister.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books", schema = "LIBRARY_REGISTER")
public class Book {

    @Id
    private Integer id;
    private String title;
    private Integer authorId;
    private Integer currentReaderId;

}
