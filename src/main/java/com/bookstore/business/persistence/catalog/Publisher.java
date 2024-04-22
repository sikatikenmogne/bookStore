package com.bookstore.business.persistence.catalog;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "editeurs")
public class Publisher implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EDITEUR", nullable = false)
    private Long id;

    @Column(name = "RAISON_SOCIALE", nullable = false)
    private String name;

//    @Column(name = "RUE")
//    private String rue;
//
//    @Column(name = "CODE_POSTAL")
//    private Long codePostal;
//
//    @Column(name = "VILLE")
//    private String ville;
//
//    @Column(name = "PAYS", length = 25)
//    private String pays;
    @Embedded
    private Address address;

    public List<Book> getBooks() {
        return books;
    }

    @OneToMany(mappedBy = "publisher")
    private List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getRue() {
//        return rue;
//    }
//
//    public void setRue(String rue) {
//        this.rue = rue;
//    }
//
//    public Long getCodePostal() {
//        return codePostal;
//    }
//
//    public void setCodePostal(Long codePostal) {
//        this.codePostal = codePostal;
//    }
//
//    public String getVille() {
//        return ville;
//    }
//
//    public void setVille(String ville) {
//        this.ville = ville;
//    }
//
//    public String getPays() {
//        return pays;
//    }
//
//    public void setPays(String pays) {
//        this.pays = pays;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publisher)) {
            return false;
        }
        Publisher other = (Publisher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Publisher[id=" + id + "]";
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}