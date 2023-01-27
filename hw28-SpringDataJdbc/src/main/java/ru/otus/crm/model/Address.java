package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;


@Table("address")
public class Address implements Cloneable {

    @Id
    private Long id;
    private String street;
    private String zipCode;

    public Address() {
    }

    public Address(String street, String zipCode) {
        this(null, street, zipCode);
    }

    @PersistenceCreator
    public Address(Long id, String street, String zipCode) {
        this.id = id;
        this.street = street;
        this.zipCode = zipCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
