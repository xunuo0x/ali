package com.alinopy.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Snow on 2017/5/23.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByUsername(String username);
}