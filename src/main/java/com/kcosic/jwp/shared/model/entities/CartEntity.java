package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Cart", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "cartGraph",
        attributeNodes = {
                @NamedAttributeNode(value="customer", subgraph = "customer"),
                @NamedAttributeNode(value="currentCustomer", subgraph = "customer"),
                @NamedAttributeNode(value="history"),
                @NamedAttributeNode(value="cartItems", subgraph = "cartItems")
        },
        subgraphs = {
                @NamedSubgraph(name="customer",
                        attributeNodes = {
                                @NamedAttributeNode(value="currentCart"),
                                @NamedAttributeNode(value="carts"),
                                @NamedAttributeNode(value="addresses"),
                                @NamedAttributeNode(value="defaultAddress")
                        }
                ),
                @NamedSubgraph(name="cartItems",
                        attributeNodes = {
                                @NamedAttributeNode(value="item")
                        }

                )
        }
)
public class CartEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "customerId", nullable = false)
    private Integer customerId;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private CustomerEntity customer;

    @OneToMany
    private Collection<CartItemEntity> cartItems = new java.util.ArrayList<>();

    @OneToOne(mappedBy = "currentCart")
    private CustomerEntity currentCustomer;

    @OneToOne(mappedBy = "cart")
    private HistoryEntity history;

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
        return Objects.equals(id, that.id) && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId);
    }

    public CustomerEntity getCustomer() {
        return customer;
    }
    public Collection<CartItemEntity> getCartItems() {
        return cartItems;
    }
    public HistoryEntity getHistory() {
        return history;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public void setCartItems(Collection<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public CustomerEntity getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(CustomerEntity currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public void setHistory(HistoryEntity history) {
        this.history = history;
    }

    @Override
    public String getGraphName() {
        return "cartGraph";
    }
}
