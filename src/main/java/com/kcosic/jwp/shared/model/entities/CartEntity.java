package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "Cart", schema = "dbo", catalog = "JWPProject")
public class CartEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @OneToMany(mappedBy = "cartByCartId")
    private Collection<CartItemEntity> cartItemsById;
    @OneToMany(mappedBy = "cartByCartId")
    private Collection<HistoryEntity> historiesById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartEntity that = (CartEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Collection<CartItemEntity> getCartItemsById() {
        return cartItemsById;
    }

    public void setCartItemsById(Collection<CartItemEntity> cartItemsById) {
        this.cartItemsById = cartItemsById;
    }

    public Collection<HistoryEntity> getHistoriesById() {
        return historiesById;
    }

    public void setHistoriesById(Collection<HistoryEntity> historiesById) {
        this.historiesById = historiesById;
    }
}
