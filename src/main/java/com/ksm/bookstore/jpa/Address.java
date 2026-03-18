package com.ksm.bookstore.jpa;

import com.ksm.bookstore.util.TableConstants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Entity that stores and creates addresses for customers. Shipping and billing
 * addresses are differentiated by foreign key references form Customer entity.
 *
 */

@Entity
@Table(name = TableConstants.ADDRESS)
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
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
}