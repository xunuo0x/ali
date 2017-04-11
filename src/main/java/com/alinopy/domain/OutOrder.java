package com.alinopy.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class OutOrder {
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
    private String costumer;

    @Column(nullable = false)
    private String contact;

    private String remark;

    public OutOrder() {
    }

    public OutOrder(Date createTime, String orderNo, Double totalFee, String costumer, String contact, String remark) {
        this.createTime = createTime;
        this.orderNo = orderNo;
        this.totalFee = totalFee;
        this.costumer = costumer;
        this.contact = contact;
        this.remark = remark;
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

    public String getCostumer() {
        return costumer;
    }

    public void setCostumer(String costumer) {
        this.costumer = costumer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
