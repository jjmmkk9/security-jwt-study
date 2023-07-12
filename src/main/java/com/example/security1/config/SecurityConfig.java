package com.example.security1.config;

import com.example.security1.config.auth.PrincipalDetailsService;
import com.example.security1.config.oauth.PrincipaOauth2UserService;
import com.example.security1.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화, preAuthorize랑 postAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipaOauth2UserService principaOauth2UserService;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean //해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다. 어디든지 사용가능
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.authorizeRequests()
                .antMatchers("/user/**").authenticated() //authenticted 는 인증만 되면 들어갈 수 있음
                .antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
               .and()
               .csrf().disable()
               .formLogin().loginPage("/loginForm")
               .loginProcessingUrl("/login")// /login 주소가 호출되면 시큐리티가 낚아채허 대신 로그인을 진행해준다.... 개좋다
               .defaultSuccessUrl("/") //로그인에 성공하면 메인페이지로 defaultSuccessUrl
               .and()
               .oauth2Login()
               .loginPage("/loginForm") //구글 로그인이 완료된 후 후처리가 필요함
               .userInfoEndpoint()
               .userService(principaOauth2UserService);

               return http.build();
    }
}
