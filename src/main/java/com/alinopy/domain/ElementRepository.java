package com.alinopy.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Snow on 2017/4/6.
 */
public interface ElementRepository extends JpaRepository<Element, Long> {
    Page<Element> findAllByDisabledIsFalse(Pageable pageable);
    Long countByDisabledIsFalse();
}
