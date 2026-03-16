package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;


import com.ksm.bookstore.qualifier.DatasourceSchedule;

import javax.persistence.GenerationType;

@Entity
@Table(name = "ADDRESS", schema = DatasourceSchedule.SCHEMA)
public class Address {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long addressId;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "STREET", length = 50, nullable = false)
    private String street;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CITY", length = 20, nullable = false)
    private String city;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "STATE", length = 20, nullable = false)
    private String state;

    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ZIP", length = 15, nullable = false)
    private String zip;
    
    // Constructor + Getters and Setters
    public Address() {}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
