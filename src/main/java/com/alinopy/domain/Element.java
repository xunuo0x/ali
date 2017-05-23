package com.alinopy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
@Table(name = "element")
public class Element {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double inPrice;

    @Column(nullable = false)
    private Double outPrice;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private boolean disabled = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createTime;

    @ManyToMany(mappedBy = "elements")
    private List<Supply> supplies = new ArrayList<>();

    @OneToMany(mappedBy = "element", fetch = FetchType.EAGER)
    private List<InOrderDetail> inOrderDetail = new ArrayList<>();

    @Column(columnDefinition="int default 0")
    private Integer amount = 0;

    public Element() {
    }

    public Element(String name, Double inPrice, Double outPrice, String description, String category, String brand, boolean disabled, Date createTime, List<Supply> supplies) {
        this.name = name;
        this.inPrice = inPrice;
        this.outPrice = outPrice;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.disabled = disabled;
        this.createTime = createTime;
        this.supplies = supplies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInPrice() {
        return inPrice;
    }

    public void setInPrice(Double inPrice) {
        this.inPrice = inPrice;
    }

    public Double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(Double outPrice) {
        this.outPrice = outPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<InOrderDetail> getInOrderDetail() {
        return inOrderDetail;
    }

    @JsonIgnore
    public void setInOrderDetail(List<InOrderDetail> inOrderDetail) {
        this.inOrderDetail = inOrderDetail;
    }

    @JsonIgnore
    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inPrice=" + inPrice +
                ", outPrice=" + outPrice +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", disabled=" + disabled +
                ", createTime=" + createTime +
                ", supplies=" + supplies +
                '}';
    }
}
