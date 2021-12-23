package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "Customer", schema = "dbo", catalog = "JWPProject")
public class CustomerEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "firstName", nullable = false, length = 100)
    private String firstName;
    @Basic
    @Column(name = "lastName", nullable = false, length = 100)
    private String lastName;
    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Basic
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Basic
    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;
    @Basic
    @Column(name = "currentCartId", nullable = true)
    private Integer currentCartId;
    @Basic
    @Column(name = "defaultAddressId", nullable = true)
    private Integer defaultAddressId;
    @Basic
    @Column(name = "roleId", nullable = false)
    private int roleId;
    @OneToMany(mappedBy = "customerByUserId")
    private Collection<AddressEntity> addressesById;
    @OneToMany(mappedBy = "customerByCustomerId")
    private Collection<CartEntity> cartsById;
    @ManyToOne
    @JoinColumn(name = "currentCartId", referencedColumnName = "id", insertable=false, updatable=false)
    private CartEntity cartByCurrentCartId;
    @ManyToOne
    @JoinColumn(name = "defaultAddressId", referencedColumnName = "id", insertable=false, updatable=false)
    private AddressEntity addressByDefaultAddressId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getCurrentCartId() {
        return currentCartId;
    }

    public void setCurrentCartId(Integer currentCartId) {
        this.currentCartId = currentCartId;
    }

    public Integer getDefaultAddressId() {
        return defaultAddressId;
    }

    public void setDefaultAddressId(Integer defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (id != that.id) return false;
        if (roleId != that.roleId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (currentCartId != null ? !currentCartId.equals(that.currentCartId) : that.currentCartId != null)
            return false;
        if (defaultAddressId != null ? !defaultAddressId.equals(that.defaultAddressId) : that.defaultAddressId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (currentCartId != null ? currentCartId.hashCode() : 0);
        result = 31 * result + (defaultAddressId != null ? defaultAddressId.hashCode() : 0);
        result = 31 * result + roleId;
        return result;
    }

    public Collection<AddressEntity> getAddressesById() {
        return addressesById;
    }

    public void setAddressesById(Collection<AddressEntity> addressesById) {
        this.addressesById = addressesById;
    }

    public Collection<CartEntity> getCartsById() {
        return cartsById;
    }

    public void setCartsById(Collection<CartEntity> cartsById) {
        this.cartsById = cartsById;
    }

    public CartEntity getCartByCurrentCartId() {
        return cartByCurrentCartId;
    }

    public void setCartByCurrentCartId(CartEntity cartByCurrentCartId) {
        this.cartByCurrentCartId = cartByCurrentCartId;
    }

    public AddressEntity getAddressByDefaultAddressId() {
        return addressByDefaultAddressId;
    }

    public void setAddressByDefaultAddressId(AddressEntity addressByDefaultAddressId) {
        this.addressByDefaultAddressId = addressByDefaultAddressId;
    }
}
