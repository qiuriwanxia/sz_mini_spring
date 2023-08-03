package com.sz.spring.factory.support;

import com.sz.spring.factory.config.BeanDefinition;
import com.sz.spring.factory.exception.BeansException;

public interface BeanFactory {

    /**
     * 获取bean
     * @param name
     * @return
     * @throws BeansException
     */
    Object getBean(String name) throws BeansException;

    /**
     * 注册beanDefinition实例
     * @param beanDefinition
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);

}
