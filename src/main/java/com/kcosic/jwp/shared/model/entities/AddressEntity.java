package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Address", schema = "dbo", catalog = "JWPProject")
public class AddressEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "customerId", nullable = false)
    private Integer customerId;
    @Basic
    @Column(name = "street", nullable = false, length = 100)
    private String street;
    @Basic
    @Column(name = "streetNumber", nullable = false)
    private Integer streetNumber;
    @Basic
    @Column(name = "apartmentNumber", nullable = false)
    private Integer apartmentNumber;
    @Basic
    @Column(name = "floorNumber", nullable = false)
    private Integer floorNumber;
    @Basic
    @Column(name = "zipCode", nullable = false, length = 50)
    private String zipCode;
    @Basic
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    @Basic
    @Column(name = "state", nullable = false, length = 100)
    private String state;
    @Basic
    @Column(name = "country", nullable = false, length = 100)
    private String country;
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private CustomerEntity customer;
    @OneToOne(mappedBy = "defaultAddress")
    private CustomerEntity currentCustomer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(customerId, that.customerId) && Objects.equals(street, that.street) && Objects.equals(streetNumber, that.streetNumber) && Objects.equals(apartmentNumber, that.apartmentNumber) && Objects.equals(floorNumber, that.floorNumber) && Objects.equals(zipCode, that.zipCode) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, street, streetNumber, apartmentNumber, floorNumber, zipCode, city, state, country);
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public CustomerEntity getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(CustomerEntity currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}
