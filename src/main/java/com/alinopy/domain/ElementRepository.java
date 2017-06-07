package com.alinopy.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Snow on 2017/4/6.
 */
public interface ElementRepository extends JpaRepository<Element, Long> {
    Page<Element> findAllByDisabledIsFalse(Pageable pageable);

    List<Element> findByAmountGreaterThanAndDisabledFalse(Integer amount);

    Page<Element> findByAmountGreaterThanAndDisabledFalse(Integer amount, Pageable pageable);

    Long countByDisabledIsFalse();

    Long countByDisabledIsFalseAndAmountGreaterThan(Integer amount);
}
