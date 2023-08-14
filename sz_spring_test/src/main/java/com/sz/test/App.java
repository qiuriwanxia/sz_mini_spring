package com.sz.test;

import com.sz.context.support.ClassPathXmlApplicationContext;
import com.sz.test.bean.People;

import java.util.ArrayList;

public class App {

    /**
     * 1.解析bean的定义信息
     * 2.通过bean的定义信息实例化bean
     * 3.提供beanFactory获取Bean实例
     * @param args
     */
    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        People people = (People) applicationContext.getBean("zhangsan");
        System.out.println("zhangsan = " + people);

    }

}
