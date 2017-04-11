package com.alinopy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class InOrderDetail {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long elementId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double discount;

    public InOrderDetail() {
    }

    public InOrderDetail(Long orderId, Long elementId, Integer amount, Double discount) {
        this.orderId = orderId;
        this.elementId = elementId;
        this.amount = amount;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
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
