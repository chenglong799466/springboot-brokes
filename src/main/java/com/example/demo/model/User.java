package com.example.demo.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author chenglong
 * @date 2018.3.15
 */
@Data
@Builder //构造器模式
@AllArgsConstructor
@NoArgsConstructor
@Alias(value = "user1")
public class User {

    private String id;

    private String sex;

    private int age;

    private String nickName;

}
