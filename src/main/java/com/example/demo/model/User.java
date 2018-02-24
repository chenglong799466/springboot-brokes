package com.example.demo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;

    private String sex;

    private int age;

    private String nickName;

}
