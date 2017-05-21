package com.alinopy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
@Table(name = "supply")
public class Supply{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String status = "活动中";

    @OneToMany(mappedBy = "element", fetch = FetchType.EAGER)
    private List<InOrderDetail> inOrderDetail = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="supply_element",joinColumns=@JoinColumn(name="supply_id"),
            inverseJoinColumns=@JoinColumn(name="element_id"))
    private List<Element> elements = new ArrayList<>();


    public Supply() {
    }

    public Supply(String name, String address, String email, String tel, String status,List<Element> elements) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.tel = tel;
        this.status = status;
        this.elements = elements;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Element> getElements() {
        return elements;
    }

    @JsonIgnore
    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public List<InOrderDetail> getInOrderDetail() {
        return inOrderDetail;
    }

    @JsonIgnore
    public void setInOrderDetail(List<InOrderDetail> inOrderDetail) {
        this.inOrderDetail = inOrderDetail;
    }
}
