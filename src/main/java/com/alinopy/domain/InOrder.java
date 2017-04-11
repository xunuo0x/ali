package com.alinopy.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class InOrder {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(nullable = false)
    private String orderNo;

    @Column(nullable = false)
    private Double totalFee;

    @Column(nullable = false)
    private Long supplyId;

    @Column(nullable = false)
    private String status;

    public InOrder() {
    }

    public InOrder(Date createTime, String orderNo, Double totalFee, Long supplyId, String status) {
        this.createTime = createTime;
        this.orderNo = orderNo;
        this.totalFee = totalFee;
        this.supplyId = supplyId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
