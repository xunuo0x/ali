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

    @OneToMany(mappedBy = "inOrder", fetch = FetchType.EAGER)
    private List<InOrderDetail> inOrderDetail = new ArrayList<>();

    @Column(nullable = false)
    private String status;

    public InOrder() {
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

    @JsonIgnore
    public List<InOrderDetail> getInOrderDetail() {
        return inOrderDetail;
    }

    public void setInOrderDetail(List<InOrderDetail> inOrderDetail) {
        this.inOrderDetail = inOrderDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
