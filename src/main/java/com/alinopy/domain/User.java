package com.alinopy.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Snow on 2017/4/6.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(nullable = false)
    private String level;

    public User() {
    }

    public User(String name, String pwd, Date createTime, String level) {
        this.name = name;
        this.pwd = pwd;
        this.createTime = createTime;
        this.level = level;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
