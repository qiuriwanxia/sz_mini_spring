package com.sz.test.bean;

public class ClassRoom {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "name='" + name + '\'' +
                '}';
    }
}
