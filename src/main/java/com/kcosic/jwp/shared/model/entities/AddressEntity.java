package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Address", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "addressGraph",
        attributeNodes = {
                @NamedAttributeNode(value="customer", subgraph = "customer"),
        },
        subgraphs = {
                @NamedSubgraph(name="customer",
                        attributeNodes = {
                                @NamedAttributeNode(value="carts"),
                                @NamedAttributeNode(value="addresses"),
                        }
                )
        }
)
public class AddressEntity extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "customerId", nullable = false, insertable = false, updatable = false)
    private Integer customerId;
    @Basic
    @Column(name = "street", nullable = false, length = 100)
    private String street;
    @Basic
    @Column(name = "streetNumber", nullable = false, length = 100)
    private String streetNumber;
    @Basic
    @Column(name = "apartmentNumber", nullable = true, length = 100)
    private String apartmentNumber;
    @Basic
    @Column(name = "floorNumber", nullable = true, length = 100)
    private String floorNumber;
    @Basic
    @Column(name = "zipCode", nullable = false, length = 50)
    private String zipCode;
    @Basic
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    @Basic
    @Column(name = "county", nullable = true, length = 100)
    private String county;
    @Basic
    @Column(name = "country", nullable = false, length = 100)
    private String country;
    @Basic
    @Column(name = "isDefault", nullable = false)
    private Boolean isDefault;
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false)
    private CustomerEntity customer;

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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(customerId, that.customerId) && Objects.equals(street, that.street) && Objects.equals(streetNumber, that.streetNumber) && Objects.equals(apartmentNumber, that.apartmentNumber) && Objects.equals(floorNumber, that.floorNumber) && Objects.equals(zipCode, that.zipCode) && Objects.equals(city, that.city) && Objects.equals(county, that.county) && Objects.equals(country, that.country) && Objects.equals(isDefault, that.isDefault);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, street, streetNumber, apartmentNumber, floorNumber, zipCode, city, county, country, isDefault);
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    @Override
    public String getGraphName() {
        return "addressGraph";
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return street + " " +
                streetNumber + ", "+
                (apartmentNumber != null && !apartmentNumber.isEmpty() ? "apt " + apartmentNumber + ", " : "") +
                (floorNumber != null && !floorNumber.isEmpty() ? "floor " + floorNumber + ", " : "") +
                zipCode + " " +
                city + ", " +
                county + ", " +
                country;
    }
}
