package com.sz.spring.beans.support;

import com.sz.spring.beans.config.BeanDefinition;
import com.sz.spring.beans.exception.BeansException;

public interface BeanFactory {

    /**
     * 获取bean
     *
     * @param name
     * @return
     * @throws BeansException
     */
    Object getBean(String name) throws BeansException;

    /**
     * 注册beanDefinition实例
     *
     * @param beanDefinition
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);


    boolean containsBean(String name);


    void registerBean(String beanName, Object obj);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

    void refresh();
}
