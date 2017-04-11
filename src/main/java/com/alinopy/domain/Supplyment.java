package com.alinopy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class Supplyment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long supplyId;

    @Column(nullable = false)
    private Long elementId;

    public Supplyment() {
    }

    public Supplyment(Long supplyId, Long elementId) {
        this.supplyId = supplyId;
        this.elementId = elementId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }
}
