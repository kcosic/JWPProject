package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "History", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "historyGraph",
        attributeNodes = {
                @NamedAttributeNode(value="cart", subgraph = "cart"),
        },
        subgraphs = {
                @NamedSubgraph(name="cart",
                        attributeNodes = {
                                @NamedAttributeNode(value="customer"),
                                @NamedAttributeNode(value="cartItems"),
                                @NamedAttributeNode(value="currentCustomer"),
                        }

                )
        }
)
public class HistoryEntity extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "cartId", nullable = true)
    private Integer cartId;

    @Basic
    @Column(name = "dateCreated", nullable = true)
    private Date dateCreated;

    @Basic
    @Column(name = "dateUpdated", nullable = true)
    private Date dateUpdated;

    @Basic
    @Column(name = "datePurchased", nullable = true)
    private Date datePurchased;

    @OneToOne
    @JoinColumn(name = "cartId", referencedColumnName = "id", insertable=false, updatable=false)
    private CartEntity cart;

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryEntity that = (HistoryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(cartId, that.cartId) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateUpdated, that.dateUpdated) && Objects.equals(datePurchased, that.datePurchased);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartId, dateCreated, dateUpdated, datePurchased);
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }
    @Override
    public String getGraphName() {
        return "historyGraph";
    }
}
