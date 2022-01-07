package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Item", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "itemGraph",
        attributeNodes = {
                @NamedAttributeNode(value="category"),
                @NamedAttributeNode(value="cartItems", subgraph = "cartItems"),
        },
        subgraphs = {
                @NamedSubgraph(name="cartItems",
                        attributeNodes = {
                                @NamedAttributeNode(value="item"),
                                @NamedAttributeNode(value="cart"),
                        }

                )
        }
)
public class ItemEntity extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic
    @Column(name = "manufacturer", nullable = true, length = 100)
    private String manufacturer;
    @Basic
    @Column(name = "description", nullable = true, length = 1000)
    private String description;
    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;
    @Basic
    @Column(name = "image", nullable = true, length = 100)
    private String image;


    @Basic
    @Column(name = "isActive")
    private boolean isActive;
    @Basic
    @Column(name = "categoryId", nullable = false, insertable = false, updatable = false)
    private Integer categoryId;
    @OneToMany(mappedBy = "item", cascade = CascadeType.MERGE)
    private Collection<CartItemEntity> cartItems;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(manufacturer, that.manufacturer) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(image, that.image) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manufacturer, description, price, image, categoryId);
    }

    public Collection<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Collection<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
    @Override
    public String getGraphName() {
        return "itemGraph";
    }
}
