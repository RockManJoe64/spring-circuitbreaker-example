package com.aptdev.circuitbreaker;

import org.springframework.stereotype.Service;

@Service
public class SecondaryRepository {
    public String doSomething() {
        System.out.println("Doing something else");
        return "Fallback";
    }
}
