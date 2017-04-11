package com.alinopy.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Snow on 2017/4/6.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    //用户登录
    User findUserByNameAndPwd(String name, String pwd);
}
