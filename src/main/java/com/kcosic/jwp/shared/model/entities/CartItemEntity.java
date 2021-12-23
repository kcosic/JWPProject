package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "CartItem", schema = "dbo", catalog = "JWPProject")
public class CartItemEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "cartId", nullable = false)
    private int cartId;
    @Basic
    @Column(name = "itemId", nullable = false)
    private int itemId;
    @Basic
    @Column(name = "count", nullable = false)
    private int count;
    @ManyToOne
    @JoinColumn(name = "cartId", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private CartEntity cartByCartId;
    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private ItemEntity itemByItemId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItemEntity that = (CartItemEntity) o;

        if (id != that.id) return false;
        if (cartId != that.cartId) return false;
        if (itemId != that.itemId) return false;
        if (count != that.count) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + cartId;
        result = 31 * result + itemId;
        result = 31 * result + count;
        return result;
    }

    public CartEntity getCartByCartId() {
        return cartByCartId;
    }

    public void setCartByCartId(CartEntity cartByCartId) {
        this.cartByCartId = cartByCartId;
    }

    public ItemEntity getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(ItemEntity itemByItemId) {
        this.itemByItemId = itemByItemId;
    }
}
