package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Address", schema = "dbo", catalog = "JWPProject")
public class AddressEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "userId", nullable = false)
    private int userId;
    @Basic
    @Column(name = "street", nullable = false, length = 100)
    private String street;
    @Basic
    @Column(name = "streetNumber", nullable = false)
    private int streetNumber;
    @Basic
    @Column(name = "apartmentNumber", nullable = false)
    private int apartmentNumber;
    @Basic
    @Column(name = "floorNumber", nullable = false)
    private int floorNumber;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
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

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (streetNumber != that.streetNumber) return false;
        if (apartmentNumber != that.apartmentNumber) return false;
        if (floorNumber != that.floorNumber) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + streetNumber;
        result = 31 * result + apartmentNumber;
        result = 31 * result + floorNumber;
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
