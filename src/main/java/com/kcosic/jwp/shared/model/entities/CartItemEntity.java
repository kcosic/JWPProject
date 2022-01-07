package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CartItem", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "cartItemGraph",
        attributeNodes = {
                @NamedAttributeNode(value="item", subgraph = "item"),
                @NamedAttributeNode(value="cart", subgraph = "cart"),
        },
        subgraphs = {
                @NamedSubgraph(name="item",
                        attributeNodes = {
                                @NamedAttributeNode(value="category"),
                        }
                ),
                @NamedSubgraph(name="cart",
                        attributeNodes = {
                                @NamedAttributeNode(value="customer"),
                        }

                )
        }
)
public class CartItemEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "cartId", nullable = false, insertable=false, updatable=false)
    private Integer cartId;
    @Basic
    @Column(name = "itemId", nullable = false, insertable=false, updatable=false)
    private Integer itemId;
    @Basic
    @Column(name = "count", nullable = false)
    private Integer count;
    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cartId", referencedColumnName = "id", nullable = false)
    private CartEntity cart;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "itemId", referencedColumnName = "id", nullable = false)
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemEntity that = (CartItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(cartId, that.cartId) && Objects.equals(itemId, that.itemId) && Objects.equals(count, that.count) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartId, itemId, count, price);
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

    @Override
    public String getGraphName() {
        return "cartItemGraph";
    }
    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
