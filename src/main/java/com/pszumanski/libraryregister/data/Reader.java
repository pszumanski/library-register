package com.pszumanski.libraryregister.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "readers", schema = "LIBRARY_REGISTER")
@Getter
public class Reader {

    @Id
    private Integer id;
}
