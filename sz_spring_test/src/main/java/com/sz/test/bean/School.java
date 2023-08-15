package com.sz.test.bean;

import com.sz.spring.beans.factory.annotation.Autowired;

public class School {

    @Autowired
    private ClassRoom classRoom;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "School{" +
                "classRoom=" + classRoom +
                ", name='" + name + '\'' +
                '}';
    }
}
