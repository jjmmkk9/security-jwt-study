package com.example.security1.repository;

import com.example.security1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

//crud 를 jpaRepo가 들고 있음
//@Repository 없어도 ioc가 된다. 이유는 jpaRepo를 상속했기 땜시 자동 빈등록
public interface UserRepository extends JpaRepository<User, Integer> {

    //findBy ~
    //select * from user where username = param;
    public User findByUsername(String username); // jpa query methods

}
