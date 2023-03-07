package com.aptdev.circuitbreaker;

import org.springframework.stereotype.Service;

@Service
public class PrimaryRepository {
    public String doSomething() {
        System.out.println("Doing something");
        return "Success";
    }
}
