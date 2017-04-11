package com.alinopy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class OutOrderDetail {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long elementId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double discount;

    public OutOrderDetail() {
    }

    public OutOrderDetail(Long elementId, Long orderId, Integer amount, Double discount) {
        this.elementId = elementId;
        this.orderId = orderId;
        this.amount = amount;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
