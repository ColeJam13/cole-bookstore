package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name = Address.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Address {

    static final String TABLE_NAME = "ADDRESS";
    private static final String SEQUENCE_NAME = "ADDRESS_ID_SEQ";

    @Id
    @SequenceGenerator(name = "ADDRESS_ID_GENERATOR", sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_ID_GENERATOR")
    @Setter(AccessLevel.NONE)
    private Long addressId;

    @NotNull(message = "Street {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 50)
    @Column(name = "STREET", length = 50, nullable = false)
    private String street;

    @NotNull(message = "City {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 20)
    @Column(name = "CITY", length = 20, nullable = false)
    private String city;

    @NotNull(message = "State {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 20)
    @Column(name = "STATE", length = 20, nullable = false)
    private String state;

    @NotNull(message = "Zip {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 15)
    @Column(name = "ZIP", length = 15, nullable = false)
    private String zip;
}