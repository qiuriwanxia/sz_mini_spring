package com.sz.test;

import com.sz.context.support.ClassPathXmlApplicationContext;
import com.sz.test.bean.People;
import com.sz.test.bean.School;

import java.util.ArrayList;

public class App {

    /**
     * 1.解析bean的定义信息
     * 2.通过bean的定义信息实例化bean
     * 3.提供beanFactory获取Bean实例
     * @param args
     */
    public static void main(String[] args) {
//        循环依赖测试案例
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
//        People people = (People) applicationContext.getBean("zhangsan");
//        System.out.println("zhangsan = " + people);


        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        School school = (School) applicationContext.getBean("school");
        System.out.println("school = " + school);
    }

}
