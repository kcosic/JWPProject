package com.kcosic.jwp.shared.model.entities;

import com.kcosic.jwp.servlets.PaymentServlet;
import com.kcosic.jwp.shared.enums.PaymentEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Cart", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "cartGraph",
        attributeNodes = {
                @NamedAttributeNode(value = "customer", subgraph = "customer"),
                @NamedAttributeNode(value = "cartItems", subgraph = "cartItems")
        },
        subgraphs = {
                @NamedSubgraph(name = "customer",
                        attributeNodes = {
                                @NamedAttributeNode(value = "carts"),
                                @NamedAttributeNode(value = "addresses"),
                        }
                ),
                @NamedSubgraph(name = "cartItems",
                        attributeNodes = {
                                @NamedAttributeNode(value = "item")
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
    @Column(name = "customerId", nullable = false, insertable = false, updatable = false)
    private Integer customerId;
    @Basic
    @Column(name = "totalPrice", precision = 2)
    private BigDecimal totalPrice;
    @Basic
    @Column(name = "dateOfPurchase")
    private Timestamp dateOfPurchase;
    @Basic
    @Column(name = "dateCreated")
    private Timestamp dateCreated;
    @Basic
    @Column(name = "isCurrent", nullable = false)
    private Boolean isCurrent;


    @Basic
    @Column(name = "paidWith", nullable = false)
    private Integer paidWith;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false)
    private CustomerEntity customer;
    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    private Collection<CartItemEntity> cartItems;

    @Transient
    private String totalPriceString;

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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        if (totalPrice != null) {
            this.totalPriceString = String.format("%,.2f", totalPrice.setScale(2, RoundingMode.DOWN));
        } else {
            this.totalPriceString = "0";
        }
    }

    public String getTotalPriceString() {

        if (totalPrice != null) {
            if (totalPriceString == null) {
                totalPriceString = String.format("%,.2f", totalPrice.setScale(2, RoundingMode.DOWN));
            }
            return totalPriceString;
        } else {
            return "0";
        }

    }

    public Timestamp getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Timestamp dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartEntity that = (CartEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(customerId, that.customerId) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(dateOfPurchase, that.dateOfPurchase) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(isCurrent, that.isCurrent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, totalPrice, dateOfPurchase, dateCreated, isCurrent);
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Collection<CartItemEntity> getCartItems() {
        return cartItems;
    }

    @Override
    public String getGraphName() {
        return "cartGraph";
    }

    public void setCartItems(Collection<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public void addCartItem(CartItemEntity cartItem){
        if(cartItems == null){
            cartItems = new ArrayList<>();
        }
        cartItems.add(cartItem);
        setTotalPrice(DbHelper.calculateTotalPrice(cartItems));
        DbHelper.updateCart(this);
    }

    public void removeCartItem(CartItemEntity cartItem){
        if(cartItems == null){
            cartItems = new ArrayList<>();
        }
        cartItems.remove(cartItem);
        setTotalPrice(DbHelper.calculateTotalPrice(cartItems));
        DbHelper.updateCart(this);
    }
    public void removeCartItem(Integer cartItemId){
        if(cartItems == null){
            cartItems = new ArrayList<>();
        }
        cartItems.removeIf(cartItemEntity -> Objects.equals(cartItemEntity.getId(), cartItemId));
        setTotalPrice(DbHelper.calculateTotalPrice(cartItems));

        DbHelper.updateCart(this);
    }
    public void updateCartItem(CartItemEntity cartItem){
        if(cartItems == null){
            cartItems = new ArrayList<>();
        }
        cartItems.forEach(cartItemEntity -> {
          if(Objects.equals(cartItemEntity.getId(), cartItem.getId())){
              cartItemEntity.setCount(cartItem.getCount());
          }
        });
        setTotalPrice(DbHelper.calculateTotalPrice(cartItems));
        DbHelper.updateCart(this);
    }

    public void clearCart() {
        cartItems.clear();
        setTotalPrice(DbHelper.calculateTotalPrice(cartItems));
        DbHelper.updateCart(this);
    }


    public PaymentEnum getPaidWith() {
        return switch (paidWith) {
            case 1 -> PaymentEnum.CASH;
            case 2 -> PaymentEnum.PAYPAL;
            default -> PaymentEnum.UNPAID;
        };
    }

    public void setPaidWith(PaymentEnum payment) {
        this.paidWith = payment.ordinal();
    }
}
