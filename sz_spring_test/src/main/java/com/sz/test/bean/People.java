package com.sz.test.bean;

public class People {

    public String name;
    private String age;

    private String nationNality;

    private People family;



    public People() {
    }

    public String getNationNality() {
        return nationNality;
    }

    public void setNationNality(String nationNality) {
        this.nationNality = nationNality;
    }

    public People getFamily() {
        return family;
    }

    public void setFamily(People family) {
        this.family = family;
    }

    public People(String nationNality) {
        this.nationNality = nationNality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", nationNality='" + nationNality +
                '}';
    }
}
