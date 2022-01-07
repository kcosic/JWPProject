package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Role", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "roleGraph",
        attributeNodes = {
                @NamedAttributeNode(value="customers", subgraph = "customer"),
        },
        subgraphs = {
                @NamedSubgraph(name="customer",
                        attributeNodes = {
                                @NamedAttributeNode(value="addresses"),
                                @NamedAttributeNode(value="carts"),
                        }

                )
        }
)
public class RoleEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.MERGE)
    private Collection<CustomerEntity> customers;

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
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Collection<CustomerEntity> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<CustomerEntity> customers) {
        this.customers = customers;
    }
    @Override
    public String getGraphName() {
        return "roleGraph";
    }
}
