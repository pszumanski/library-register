package com.pszumanski.libraryregister.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "readers", schema = "LIBRARY_REGISTER")
public class Reader {

    @Id
    private Integer id;
}
