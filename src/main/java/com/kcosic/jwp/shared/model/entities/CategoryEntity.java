package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Category", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "categoryGraph",
        attributeNodes = {
                @NamedAttributeNode(value="items", subgraph = "items"),
        },
        subgraphs = {
                @NamedSubgraph(name="items",
                        attributeNodes = {
                                @NamedAttributeNode(value="category"),
                                @NamedAttributeNode(value="cartItems"),
                        }
                )
        }
)
public class CategoryEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "category")
    private Collection<ItemEntity> items;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Collection<ItemEntity> getItems() {
        return items;
    }

    public void setItems(Collection<ItemEntity> items) {
        this.items = items;
    }

    @Override
    public String getGraphName() {
        return "categoryGraph";
    }
}
