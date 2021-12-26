package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "Cart", schema = "dbo", catalog = "JWPProject")
public class CartEntity extends BaseEntity{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "customerId", nullable = false)
    private Integer customerId;
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private CustomerEntity customerByCustomerId;
    @OneToMany(mappedBy = "cartByCartId")
    private Collection<CartItemEntity> cartItemsById;
    @OneToMany(mappedBy = "cartByCurrentCartId")
    private Collection<CustomerEntity> customersById;
    @OneToMany(mappedBy = "cartByCartId")
    private Collection<HistoryEntity> historiesById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartEntity that = (CartEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        return result;
    }

    public CustomerEntity getCustomerByCustomerId() {
        return customerByCustomerId;
    }

    public void setCustomerByCustomerId(CustomerEntity customerByCustomerId) {
        this.customerByCustomerId = customerByCustomerId;
    }

    public Collection<CartItemEntity> getCartItemsById() {
        return cartItemsById;
    }

    public void setCartItemsById(Collection<CartItemEntity> cartItemsById) {
        this.cartItemsById = cartItemsById;
    }

    public Collection<CustomerEntity> getCustomersById() {
        return customersById;
    }

    public void setCustomersById(Collection<CustomerEntity> customersById) {
        this.customersById = customersById;
    }

    public Collection<HistoryEntity> getHistoriesById() {
        return historiesById;
    }

    public void setHistoriesById(Collection<HistoryEntity> historiesById) {
        this.historiesById = historiesById;
    }
}
