package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "CartItem", schema = "dbo", catalog = "JWPProject")
public class CartItemEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "cartId", nullable = false)
    private Integer cartId;
    @Basic
    @Column(name = "itemId", nullable = false)
    private Integer itemId;
    @Basic
    @Column(name = "count", nullable = false)
    private Integer count;
    @ManyToOne
    @JoinColumn(name = "cartId", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private CartEntity cart;
    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private ItemEntity item;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemEntity that = (CartItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(cartId, that.cartId) && Objects.equals(itemId, that.itemId) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartId, itemId, count);
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
