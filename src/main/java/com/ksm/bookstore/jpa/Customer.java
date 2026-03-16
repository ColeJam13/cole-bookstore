package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "CUSTOMER_FIRST_NAME", length = 50, nullable = false, unique = false)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "CUSTOMER_LAST_NAME", length = 50, nullable = false, unique = false)
    private String lastName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long customerId;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CUSTOMER_EMAIL", nullable = false, unique = true)
    private String email;

    // Addresses now have own Column where the address type is identified with a primary key
    @ManyToOne
    @JoinColumn(name = "BILLING_ADDRESS_ID")
    private Address billingAddress;

    @ManyToOne
    @JoinColumn(name = "SHIPPING_ADDRESS_ID")
    private Address shippingAddress;

}
 