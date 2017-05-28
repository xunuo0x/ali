package com.alinopy.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by Snow on 2017/4/6.
 */
public interface OutOrderRepository extends JpaRepository<OutOrder, Long>{
    Page<OutOrder> findAll(Pageable pageable);

    Integer countByCreateTimeBetween(Date startDate, Date endDate);
}
