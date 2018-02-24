package com.example.demo.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class HelloWorld {

    private String china;

    private String USA;

    public String hello() {
        return "Hello World!"+china+USA;
    }
}
