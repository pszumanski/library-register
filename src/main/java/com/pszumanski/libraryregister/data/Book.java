package com.pszumanski.libraryregister.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "books", schema = "LIBRARY_REGISTER")
@Getter
public class Book {

    @Id
    private Integer id;
    private Integer authorId;
    private Integer currentReaderId;
    //TODO: Date of sql?
    private String deadline;
    private String title;
    private String publisher;
    //TODO: Year
    private String publishYear;
    private String isbn;
    private String genre;
    private String language;

}
