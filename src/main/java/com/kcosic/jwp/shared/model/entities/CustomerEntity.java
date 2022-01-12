package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Customer", schema = "dbo", catalog = "JWPProject")

@NamedEntityGraph(name = "customerGraph",
        attributeNodes = {
                @NamedAttributeNode(value="addresses"),
                @NamedAttributeNode(value="carts", subgraph = "cart"),
                @NamedAttributeNode(value="role"),
        },
        subgraphs = {
                @NamedSubgraph(name="cart",
                        attributeNodes = {
                                @NamedAttributeNode(value="cartItems"),
                        }

                )
        }
)
public class CustomerEntity extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "firstName", nullable = true, length = 100)
    private String firstName;
    @Basic
    @Column(name = "lastName", nullable = true, length = 100)
    private String lastName;
    @Basic
    @Column(name = "email", nullable = true, length = 100)
    private String email;
    @Basic
    @Column(name = "password", nullable = true, length = 100)
    private String password;
    @Basic
    @Column(name = "dateOfBirth", nullable = true)
    private Date dateOfBirth;
    @Basic
    @Column(name = "roleId", nullable = false, insertable = false, updatable = false)
    private Integer roleId;
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<AddressEntity> addresses;
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<CartEntity> carts;
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)
    private RoleEntity role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, dateOfBirth, roleId);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public Collection<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public Collection<CartEntity> getCarts() {
        return carts;
    }

    public void setCarts(Collection<CartEntity> carts) {
        this.carts = carts;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
    @Override
    public String getGraphName() {
        return "customerGraph";
    }
}
