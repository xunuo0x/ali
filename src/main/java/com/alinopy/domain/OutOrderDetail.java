package com.alinopy.domain;

import javax.persistence.*;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class OutOrderDetail {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "element_id")
    private Element element;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "outorder_id")
    private OutOrder outOrder;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double discount;

    public OutOrderDetail() {
    }

    public OutOrderDetail(Element element, OutOrder outOrder, Integer amount, Double discount) {
        this.element = element;
        this.outOrder = outOrder;
        this.amount = amount;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public OutOrder getOutOrder() {
        return outOrder;
    }

    public void setOutOrder(OutOrder outOrder) {
        this.outOrder = outOrder;
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
