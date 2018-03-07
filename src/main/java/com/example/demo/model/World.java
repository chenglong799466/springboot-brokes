package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public enum World {

    China("中国"),
    USA("美国");

    private String name;

    World(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
