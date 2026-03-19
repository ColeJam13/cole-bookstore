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

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "CUSTOMER_FIRST_NAME", length = 20, nullable = false, unique = false)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "CUSTOMER_LAST_NAME", length = 20, nullable = false, unique = false)
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

    // Billing and shipping addresses are seperate Address entities
    // distinguished by their own foreign key columns on this table
    @ManyToOne
    @JoinColumn(name = "BILLING_ADDRESS_ID")
    private Address billingAddress;

    @ManyToOne
    @JoinColumn(name = "SHIPPING_ADDRESS_ID")
    private Address shippingAddress;

}
 