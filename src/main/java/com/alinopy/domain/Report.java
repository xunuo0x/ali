package com.alinopy.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Snow on 2017/5/23.
 */
@Entity
public class Report {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double price;

    public Report() {
    }

}
