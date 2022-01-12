package com.kcosic.jwp.shared.model.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Log", schema = "dbo", catalog = "JWPProject")
@NamedEntityGraph(name = "logGraph")
public class LogEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "customer", nullable = false, length = 100)
    private String customer;
    @Basic
    @Column(name = "ipAddress", nullable = false, length = 100)
    private String ipAddress;
    @Basic
    @Column(name = "actionName", nullable = false, length = 100)
    private String actionName;
    @Basic
    @Column(name = "actionTime", nullable = false)
    private Date actionTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntity logEntity = (LogEntity) o;
        return Objects.equals(id, logEntity.id) && Objects.equals(customer, logEntity.customer) && Objects.equals(ipAddress, logEntity.ipAddress) && Objects.equals(actionName, logEntity.actionName) && Objects.equals(actionTime, logEntity.actionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, ipAddress, actionName, actionTime);
    }

    @Override
    public String getGraphName() {
        return "logGraph";
    }
}
