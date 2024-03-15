package com.marlonnunes.carrental;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Test {

    @Value("${bd.user}")
    public String user;

    @PostConstruct
    public void test(){
        System.out.println(user);
    }
}
