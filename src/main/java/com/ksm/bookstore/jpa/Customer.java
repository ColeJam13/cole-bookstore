package com.ksm.bookstore.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ksm.bookstore.qualifier.DatasourceSchedule;

@Entity
@Table(name = "CUSTOMER", schema = DatasourceSchedule.SCHEMA)
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
    private Long customerId;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CUSTOMER_EMAIL", nullable = false, unique = true)
    private String email;

    // Embedding the Address from an outside entity to prevent mixup between Billing and Shipping columns (LOOK AT THIS)
    @Embedded                                       
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "BILL_STREET")),
        @AttributeOverride(name = "city", column = @Column(name = "BILL_CITY")),
        @AttributeOverride(name = "state", column = @Column(name = "BILL_STATE")),
        @AttributeOverride(name = "zip", column = @Column(name = "BILL_ZIP"))
    })
    private Address billingAddress;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "SHIP_STREET")),
        @AttributeOverride(name = "city", column = @Column(name = "SHIP_CITY")),
        @AttributeOverride(name = "state", column = @Column(name = "SHIP_STATE")),
        @AttributeOverride(name = "zip", column = @Column(name = "SHIP_ZIP"))
    })
    private Address shippingAddress;

    // Constructor + Getters and Setters
    public Customer() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    // Doesn't need setter, hibernate generates it
    public Long getCustomerId() {
        return customerId;
    }
}
 