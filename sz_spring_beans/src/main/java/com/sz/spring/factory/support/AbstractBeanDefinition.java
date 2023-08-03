package com.sz.spring.factory.support;

import com.sz.spring.factory.config.BeanDefinition;

public abstract class AbstractBeanDefinition implements BeanDefinition {

    String id;

    String beanClassName;


    //保证线程安全
    volatile Class beanClass;

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
