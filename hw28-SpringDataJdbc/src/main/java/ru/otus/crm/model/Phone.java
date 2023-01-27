package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("phone")
public class Phone implements Cloneable {

    @Id
    private Long id;

    private String phoneNumber;

    public Phone() {
    }

    public Phone(String phoneNumber) {
        this(null, phoneNumber);
    }

    @PersistenceCreator
    public Phone(Long id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "phone{" +
                "id=" + id +
                ", number='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone that = (Phone) o;
        return id == that.id &&
                phoneNumber == that.phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber);
    }

}
