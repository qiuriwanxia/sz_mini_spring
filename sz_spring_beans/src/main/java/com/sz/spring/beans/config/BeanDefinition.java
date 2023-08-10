package com.sz.spring.beans.config;

public interface BeanDefinition {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    String getId();

    Class getBeanClass();

    String getBeanClassName();

    void setId(String id);

    void setBeanClass(Class beanClass);

    void setBeanClassName(String className);

}
