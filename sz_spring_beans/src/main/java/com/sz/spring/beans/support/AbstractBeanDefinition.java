package com.sz.spring.beans.support;

import com.sz.spring.beans.config.BeanDefinition;
import com.sz.spring.beans.factory.ConstructorArgumentValues;
import com.sz.spring.beans.factory.PropertieValues;

public abstract class AbstractBeanDefinition implements BeanDefinition {

    String id;

    String beanClassName;

    /**
     * 是否懒加载
     */
    private boolean lazyinit;

    /**
     * 构造函数注入属性集合
     */
    private ConstructorArgumentValues constructorArgumentValues;


    /**
     * 注入属性集合
     */
    private PropertieValues propertieValues;


    /**
     * 初始化方法名称
     */
    private String initMethodName;



    //保证线程安全
    volatile Class beanClass;


    public boolean isLazyinit() {
        return lazyinit;
    }

    public void setLazyinit(boolean lazyinit) {
        this.lazyinit = lazyinit;
    }

    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(ConstructorArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    public PropertieValues getPropertieValues() {
        return propertieValues;
    }

    public void setPropertieValues(PropertieValues propertieValues) {
        this.propertieValues = propertieValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public Class getBeanClass() {
        return beanClass;
    }

    @Override
    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
