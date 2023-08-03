package com.sz.spring.factory.config;

public interface BeanDefinition {

    String getId();

    Class getBeanClass();

    String getBeanClassName();

    void setId(String id);

    void setBeanClass(Class beanClass);

    void setBeanClassName(String className);

}
