package com.example.security1.controller;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encodePwd;

    @GetMapping({"", "/"})
    public String index(){
        //머스테치 기본 폴더 src/main/resources/
        //뷰리졸버 설정: templates (prefix), .mastache(subfix)
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){

        return "user";
    }
    @GetMapping("/admin")
    public @ResponseBody String admin(){

        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){

        return "manager";
    }
    //스프링 시큐리티가 해당 주소를 낚아채버리네요! - SecurityConfig 파일 생성후 작동 안하고 우리가 만든 로그인 페이지로
    @GetMapping("/loginForm")
    public String loginForm(){

        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm(){

        return "joinForm";
    }

    @PostMapping("/join")
    public String join( User user){
        user.setRole("USER");
        String rawPassword = user.getPassword();
        String encPassword = encodePwd.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 회원가입 잘됨 그러나 시큐리티로 로그인 할 수 없다. => 패스워드 암호화가 안되어있어몃
        System.out.println(user);

        return "redirect:/loginForm";
    }
}
