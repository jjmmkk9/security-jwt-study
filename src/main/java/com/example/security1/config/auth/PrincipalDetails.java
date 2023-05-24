package com.example.security1.config.auth;

//시큐리티가 /login을 낚아채서 로그인을 진행시킨다. 이때 로그인 진행 완료가 되면 session에 넣어주는데
//시큐리티가 가지는 session이 따로 있다. (Security Context Holder에 정보를 저장한다.)
//이 세션에 들어갈수 있는 obj는 정해져있다 => Authentication 객체
//Authentication 의 User 정보 => UserDetails 객체


import com.example.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user; //콤포지션

    public PrincipalDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();

        collect.add((GrantedAuthority) () -> user.getRole());

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 함
        //현재 시간 - 로그인시간 = 1년 초과하면 false

        return true;
    }
}
