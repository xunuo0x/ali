package com.alinopy.domain;

import javax.persistence.*;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class InOrderDetail {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "element_id")
    private Element element;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "inorder_id")
    private InOrder inOrder;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double discount;

    public InOrderDetail() {
    }

    public InOrderDetail(InOrder inOrder, Element element, Supply supply, Integer amount, Double discount) {
        this.inOrder = inOrder;
        this.element = element;
        this.supply = supply;
        this.amount = amount;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InOrder getInOrder() {
        return inOrder;
    }

    public void setInOrder(InOrder inOrder) {
        this.inOrder = inOrder;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
