package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Cart", schema = "dbo", catalog = "JWPProject")
public class CartEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "customerId", nullable = false)
    private int customerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartEntity that = (CartEntity) o;

        if (id != that.id) return false;
        if (customerId != that.customerId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + customerId;
        return result;
    }
}
