package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.CascadeType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Entity representing a customer object.
 * 
 */

@Entity
@Table(name = Customer.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    static final String TABLE_NAME = "CUSTOMER";
    private static final String SEQUENCE_NAME = "CUSTOMER_ID_SEQ";

    @Id
    @SequenceGenerator(name = "CUSTOMER_ID_GENERATOR", sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_ID_GENERATOR")
    @Setter(AccessLevel.NONE)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @NotNull(message = "Customer First Name {javax.validation.constraints.NotNull.message}")
    @Size(min = 2, max = 20)
    @Column(name = "CUSTOMER_FIRST_NAME", length = 20, nullable = false, unique = false)
    private String firstName;

    @NotNull(message = "Customer Last Name {javax.validation.constraints.NotNull.message}")
    @Size(min = 2, max = 20)
    @Column(name = "CUSTOMER_LAST_NAME", length = 20, nullable = false, unique = false)
    private String lastName;

    @NotNull(message = "Customer Email {javax.validation.constraints.NotNull.message}")
    @Size(min = 1, max = 50)
    @Column(name = "CUSTOMER_EMAIL", nullable = false, unique = true)
    private String email;

    // Billing and shipping addresses are seperate Address entities
    // distinguished by their own foreign key columns on this table
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "BILLING_ADDRESS_ID")
    private Address billingAddress;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "SHIPPING_ADDRESS_ID")
    private Address shippingAddress;

}
 