package com.alinopy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class Storage {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long elementId;

    @Column(nullable = false)
    private Integer storage;

    public Storage() {
    }

    public Storage(Long elementId, Integer storage) {
        this.elementId = elementId;
        this.storage = storage;
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

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }
}
