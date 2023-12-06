package com.pszumanski.libraryregister.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "authors", schema = "LIBRARY_REGISTER")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String bornDate;
    private String deathDate;

    public Author(String name, String bornDate, String deathDate) {
        this.name = name;
        this.bornDate = bornDate;
        this.deathDate = deathDate;
    }

    protected Author() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Author author = (Author) object;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(bornDate, author.bornDate) && Objects.equals(deathDate, author.deathDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bornDate, deathDate);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bornDate='" + bornDate + '\'' +
                ", deathDate='" + deathDate + '\'' +
                '}';
    }
}
